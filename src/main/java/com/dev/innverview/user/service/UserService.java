package com.dev.innverview.user.service;
import com.dev.innverview.user.domain.User;
import com.dev.innverview.user.domain.UserRepository;
import com.dev.innverview.video.domain.VideoContent;
import com.dev.innverview.video.domain.VideoContentRepository;
import com.dev.innverview.video.domain.VideoProject;
import com.dev.innverview.video.domain.VideoProjectRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VideoContentRepository contentRepository;
    private final VideoProjectRepository projectRepository;

    public User register(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        return userRepository.save(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public User update(String email, String username, String profileImage) {
        User user = getByEmail(email);
        user.setUsername(username);
        user.setProfileImage(profileImage);
        return userRepository.save(user);
    }

    public List<VideoContent> myVideos(String email) {
        User user = getByEmail(email);
        List<VideoProject> projects = projectRepository.findByUser(user);
        return contentRepository.findAll().stream()
                .filter(c -> projects.contains(c.getProject()))
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
