package com.wcs.mtgbox.transaction.offer.infrastructure;

import com.wcs.mtgbox.transaction.offer.domain.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}