package com.dev.innverview.service;

import com.dev.innverview.domain.InnerView;
import com.dev.innverview.domain.InnerViewRepository;
import com.dev.innverview.domain.InnerViewType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InnerViewService {

    private final InnerViewRepository repository;

    public List<InnerView> findAll() {
        return repository.findAll();
    }

    public InnerView create(String title, InnerViewType type) {
        InnerView innerView = InnerView.builder()
                .title(title)
                .type(type)
                .build();
        return repository.save(innerView);
    }

    public InnerView findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("InnerView not found"));
    }
}
