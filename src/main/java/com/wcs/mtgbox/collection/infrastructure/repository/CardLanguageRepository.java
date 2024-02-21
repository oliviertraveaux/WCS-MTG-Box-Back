package com.wcs.mtgbox.collection.infrastructure.repository;

import com.wcs.mtgbox.collection.domain.entity.CardLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardLanguageRepository extends JpaRepository<CardLanguage, Long> {
}
