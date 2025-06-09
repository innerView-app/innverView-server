package com.dev.innverview.video.dto;

import com.dev.innverview.video.domain.Video;
import com.dev.innverview.video.domain.VideoStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VideoProfile {
    String id;
    String title;
    VideoStatus status;
    String owner;
    String creatAt;
    String eventResult;

    public VideoProfile(Video video) {
        this.id = video.getId().toString();
        this.title = video.getTitle();
        this.status = video.getStatus();
        this.owner = video.getOwner();
        this.creatAt = video.getCreateAt().toString();
        this.eventResult = video.getEventResult();
    }
}
