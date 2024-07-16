package com.wcs.mtgbox.transaction.offer.infrastructure;

import com.wcs.mtgbox.transaction.offer.domain.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByWantedUserCard_Id(Long userCardId);
    List<Offer> findAllByUser_IdOrderByLastModificationDateDesc(Long userId);
    List<Offer> findAllByWantedUserCard_User_IdOrderByCreatedDateDesc(Long userId);
    List<Offer> findAllByUser_IdOrWantedUserCard_User_IdOrderByLastModificationDateDesc(Long userId, Long wantedUserCardUserId);
}