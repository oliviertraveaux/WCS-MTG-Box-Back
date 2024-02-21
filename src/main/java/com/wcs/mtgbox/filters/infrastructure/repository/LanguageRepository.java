package com.wcs.mtgbox.filters.infrastructure.repository;

import com.wcs.mtgbox.collection.domain.entity.CardLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<CardLanguage, Long> {
}
