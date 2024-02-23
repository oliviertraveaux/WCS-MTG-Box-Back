package com.wcs.mtgbox.filters.infrastructure.repository;

import com.wcs.mtgbox.filters.domain.entity.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardSetRepository extends JpaRepository<CardSet, Long> {
}
