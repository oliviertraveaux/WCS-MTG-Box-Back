package com.wcs.mtgbox.filters.infrastructure.repository;

import com.wcs.mtgbox.filters.domain.entity.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTypeRepository extends JpaRepository<CardType, Long> {
}
