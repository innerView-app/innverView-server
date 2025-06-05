package com.dev.innverview.data.video;

import com.dev.innverview.domain.Video;
import com.dev.innverview.domain.VideoStatus;
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
