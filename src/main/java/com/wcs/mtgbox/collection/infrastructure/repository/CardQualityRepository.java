package com.wcs.mtgbox.collection.infrastructure.repository;

import com.wcs.mtgbox.collection.domain.entity.CardQuality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardQualityRepository extends JpaRepository<CardQuality, Long> {
}
