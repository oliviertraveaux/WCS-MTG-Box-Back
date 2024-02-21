package com.wcs.mtgbox.filters.infrastructure.repository;

import com.wcs.mtgbox.filters.domain.entity.CardRarity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RarityRepository extends JpaRepository<CardRarity, Long> {
}
