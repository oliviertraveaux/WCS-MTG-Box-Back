package com.wcs.mtgbox.transaction.offer.infrastructure;

import com.wcs.mtgbox.transaction.offer.domain.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByWantedUserCard_Id(Long userCardId);
    List<Offer> findAllByUser_IdOrderByLastModificationDateDesc(Long userId);
    List<Offer> findAllByWantedUserCard_User_IdOrderByCreatedDateDesc(Long userId);
    List<Offer> findAllByUser_IdOrWantedUserCard_User_IdOrderByLastModificationDateDesc(Long userId, Long wantedUserCardUserId);
    @Query(value = "SELECT o FROM Offer o JOIN o.userCards uc WHERE uc.id = :userCardId")
    List<Offer> findOffersByUserCardId(@Param("userCardId") Long userCardId);
}