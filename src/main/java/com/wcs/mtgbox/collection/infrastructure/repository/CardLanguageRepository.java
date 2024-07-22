package com.wcs.mtgbox.collection.infrastructure.repository;

import com.wcs.mtgbox.collection.domain.entity.CardLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardLanguageRepository extends JpaRepository<CardLanguage, Long> {
    Optional<CardLanguage> findByName(String languageName);

}
