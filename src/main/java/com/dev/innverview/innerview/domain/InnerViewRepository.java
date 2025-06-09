package com.dev.innverview.innerview.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InnerViewRepository extends JpaRepository<InnerView, UUID> {
}
