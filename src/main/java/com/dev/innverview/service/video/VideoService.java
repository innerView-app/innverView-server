package com.dev.innverview.service.video;

import com.dev.innverview.data.video.VideoProfile;
import com.dev.innverview.exception.DoesNotExist;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;
import java.util.UUID;

public interface VideoService {
    public VideoProfile createFromObjectStorage(UUID userId, String title, String objectName);

    VideoProfile upload(MultipartFile file, String fileName) throws Exception;

    StreamingResponseBody m3u8Index(UUID videoId) throws DoesNotExist;

    StreamingResponseBody ts(UUID videoId, String ts) throws DoesNotExist;

    List<VideoProfile> list();

//    VideoProfile profile(UUID videoId) throws DoesNotExist;

//    void onVideoEventComplete(EventResult eventResult) throws Exception;
}
