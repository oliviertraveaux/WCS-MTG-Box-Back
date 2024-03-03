package com.wcs.mtgbox.collection.infrastructure.repository;

import com.wcs.mtgbox.collection.domain.entity.CardQuality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardQualityRepository extends JpaRepository<CardQuality, Long> {
    Optional<CardQuality> findByName(String qualityName);
}
