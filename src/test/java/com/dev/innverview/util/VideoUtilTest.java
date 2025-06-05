package com.dev.innverview.util;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

class VideoUtilTest {

    @Test
    void streamIndexAddsPrefix() throws Exception {
        String index = "#EXTM3U\n0.ts\n";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(index.getBytes())));
        StreamingResponseBody body = VideoUtil.streamIndex(reader, "http://test/");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        body.writeTo(out);
        assertThat(out.toString()).contains("http://test/0.ts");
    }

    @Test
    void streamFileCopiesInput() throws Exception {
        byte[] data = {1,2,3};
        StreamingResponseBody body = VideoUtil.streamFile(new ByteArrayInputStream(data));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        body.writeTo(out);
        assertThat(out.toByteArray()).containsExactly(data);
    }
}
