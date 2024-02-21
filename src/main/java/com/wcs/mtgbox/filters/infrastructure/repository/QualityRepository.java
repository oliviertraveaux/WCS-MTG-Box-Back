package com.wcs.mtgbox.filters.infrastructure.repository;

import com.wcs.mtgbox.collection.domain.entity.CardQuality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualityRepository extends JpaRepository<CardQuality, Long> {
}
