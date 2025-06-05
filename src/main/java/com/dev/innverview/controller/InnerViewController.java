package com.dev.innverview.controller;

import com.dev.innverview.domain.InnerView;
import com.dev.innverview.domain.InnerViewType;
import com.dev.innverview.service.InnerViewService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/innerviews")
@RequiredArgsConstructor
public class InnerViewController {

    private final InnerViewService service;

    @GetMapping
    public List<InnerView> list() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<InnerView> create(@RequestBody InnerViewRequest request) {
        InnerView created = service.create(request.getTitle(), request.getType());
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InnerView> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Data
    public static class InnerViewRequest {
        private String title;
        private InnerViewType type;
    }
}
