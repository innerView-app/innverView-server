package com.dev.innverview.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VideoUtil {
    private static final Logger logger = LoggerFactory.getLogger(VideoUtil.class);

    public static void transcodeToM3u8(File source, File workDir) throws IOException {
        List<String> command = mp4ToHlsCommand(source.getAbsolutePath());
        Process process = new ProcessBuilder().command(command).directory(workDir).start();

        new Thread(
                () -> {
                    try (BufferedReader bufferedReader =
                                 new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) {
                            logger.info(line);
                        }
                    } catch (IOException e) {
                    }
                })
                .start();

        new Thread(
                () -> {
                    try (BufferedReader bufferedReader =
                                 new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) {
                            logger.info(line);
                        }
                    } catch (IOException e) {
                    }
                })
                .start();

        try {
            if (process.waitFor() != 0) {
                throw new RuntimeException("exit with " + process.exitValue());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> mp4ToHlsCommand(String src) {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(src);
        command.add("-hls_time");
        command.add("10");
        command.add("-hls_list_size");
        command.add("0");
        command.add("-hls_segment_filename");
        command.add("%d.ts");
        command.add("index.m3u8");
        return command;
    }

    public static org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody streamIndex(java.io.BufferedReader reader, String prefix) {
        return outputStream -> {
            try (reader) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.endsWith(".ts")) {
                        line = prefix + line;
                    }
                    outputStream.write(line.getBytes());
                    outputStream.write(System.lineSeparator().getBytes());
                }
                outputStream.flush();
            }
        };
    }

    public static org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody streamFile(java.io.InputStream inputStream) {
        return outputStream -> {
            try (inputStream) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
        };
    }
}
