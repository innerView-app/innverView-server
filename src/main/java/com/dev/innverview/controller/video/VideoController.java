package com.dev.innverview.controller.video;

import com.dev.innverview.data.video.VideoRequest;
import com.dev.innverview.exception.DoesNotExist;
import com.dev.innverview.service.video.VideoService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.UUID;

@Controller
@RequestMapping(path = "/api/video")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping(
            path = "/{id}/index.m3u8",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> index(@PathVariable("id") String id, @Valid @ParameterObject VideoRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/vnd.apple.mpegurl");
            headers.set("Content-Disposition", "attachment;filename=index.m3u8");
            StreamingResponseBody body = videoService.m3u8Index(UUID.fromString(request.getId()));
            return new ResponseEntity<>(body, headers, HttpStatus.OK);
        } catch (DoesNotExist e) {
            return ResponseEntity.notFound().build();
        }
    }
}
