package com.dev.innverview.service;

import com.dev.innverview.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoInteractionService {

    private final VideoContentRepository contentRepository;
    private final VideoViewRepository viewRepository;
    private final VideoLikeRepository likeRepository;
    private final VideoCommentRepository commentRepository;
    private final UserRepository userRepository;

    public void addView(UUID contentId, String email) {
        VideoContent content = contentRepository.findById(contentId).orElseThrow();
        User user = null;
        if (email != null) {
            user = userRepository.findByEmail(email).orElse(null);
        }
        VideoView view = VideoView.builder()
                .content(content)
                .user(user)
                .build();
        viewRepository.save(view);
    }

    public void like(UUID contentId, String email) {
        VideoContent content = contentRepository.findById(contentId).orElseThrow();
        User user = userRepository.findByEmail(email).orElseThrow();
        if (!likeRepository.existsByContentAndUser(content, user)) {
            VideoLike like = VideoLike.builder()
                    .content(content)
                    .user(user)
                    .build();
            likeRepository.save(like);
        }
    }

    public VideoComment comment(UUID contentId, String email, String text) {
        VideoContent content = contentRepository.findById(contentId).orElseThrow();
        User user = userRepository.findByEmail(email).orElseThrow();
        VideoComment comment = VideoComment.builder()
                .content(content)
                .user(user)
                .text(text)
                .build();
        return commentRepository.save(comment);
    }

    public List<VideoComment> comments(UUID contentId) {
        VideoContent content = contentRepository.findById(contentId).orElseThrow();
        return commentRepository.findByContent(content);
    }
}
