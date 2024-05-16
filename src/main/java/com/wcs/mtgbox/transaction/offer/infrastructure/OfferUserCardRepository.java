package com.wcs.mtgbox.transaction.offer.infrastructure;

import com.wcs.mtgbox.transaction.offer.domain.entity.OfferUserCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferUserCardRepository extends JpaRepository<OfferUserCard, Long> {
}
