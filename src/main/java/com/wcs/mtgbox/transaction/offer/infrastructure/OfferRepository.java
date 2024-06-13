package com.wcs.mtgbox.transaction.offer.infrastructure;

import com.wcs.mtgbox.transaction.offer.domain.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByWantedUserCard_Id(Long userCardId);
    List<Offer> findAllByUser_Id(Long userId);
    List<Offer> findAllByWantedUserCard_User_Id(Long userId);

}