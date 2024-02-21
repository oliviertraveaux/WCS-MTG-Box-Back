package com.wcs.mtgbox.collection.infrastructure.repository;

import com.wcs.mtgbox.collection.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findCardByApiCardId(String apiId);
}
