package com.dev.innverview.service.video;

import com.dev.innverview.data.video.VideoProfile;
import com.dev.innverview.domain.Video;
import com.dev.innverview.domain.VideoRepository;
import com.dev.innverview.domain.VideoStatus;
import com.dev.innverview.exception.DoesNotExist;
import com.dev.innverview.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;
import com.dev.innverview.util.VideoUtil;

@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    StorageService localStorageService;



    @Value("${file.server-path}")
    private String videoPrefix;

    @Override
    public VideoProfile createFromObjectStorage(UUID userId, String title, String objectName) {
        return null;
    }

    @Async
    @Override
    public VideoProfile upload(MultipartFile file, String fileName) throws Exception {


        return null;
    }


    @Override
    public StreamingResponseBody m3u8Index(UUID videoId) throws DoesNotExist {
        Video video = videoRepository.findById(videoId).orElseThrow();
        if (video.getStatus().equals(VideoStatus.PROCESSING)) {
            throw new DoesNotExist("Video is still processing");
        }

        String m3u8Prefix = videoPrefix + video.getId() + "/";

        try {
            List<String> files =
                    localStorageService.listFiles(video.getId().toString());
            String target =
                    files.stream()
                            .filter(file -> file.endsWith("index.m3u8"))
                            .findFirst()
                            .orElseThrow(() -> new DoesNotExist("index.m3u8 not found"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    localStorageService.readFile(video.getId().toString(), target)));
            return VideoUtil.streamIndex(reader, m3u8Prefix);
        } catch (IOException e) {
            throw new DoesNotExist("Error reading m3u8 file");
        }
    }

    @Override
    public StreamingResponseBody ts(UUID videoId, String ts) throws DoesNotExist {
        Video video = videoRepository.findById(videoId).orElseThrow();
        if (video.getStatus().equals(VideoStatus.PROCESSING)) {
            throw new DoesNotExist("Video is still processing");
        }
        try {
            InputStream inputStream = localStorageService.readFile(video.getId().toString(), ts);
            return VideoUtil.streamFile(inputStream);
        } catch (IOException e) {
            throw new DoesNotExist("Error reading ts file");
        }
    }

    @Override
    public List<VideoProfile> list() {
        return videoRepository.findAll().stream()
                .map(VideoProfile::new)
                .toList();
    }
}
