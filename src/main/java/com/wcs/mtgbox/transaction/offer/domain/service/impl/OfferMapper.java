package com.wcs.mtgbox.transaction.offer.domain.service.impl;

import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferDto;
import com.wcs.mtgbox.transaction.offer.domain.entity.Offer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferMapper {
    public Offer offerDtoToOfferEntity(User user, List<UserCard> userCards, UserCard wantedUserCard) {
        Offer offer = new Offer();
        offer.setWantedUserCard(wantedUserCard);
        offer.setUser(user);
        offer.setUserCards(userCards);
        offer.setStatus("PENDING");
        offer.setCreatedDate(LocalDateTime.now());
        return offer;
    }

    public OfferDto offerEntityToOfferDto(Offer offer) {
        OfferDto offerDto = new OfferDto();
        offerDto.setUserId(offer.getUser().getId());
        offerDto.setUserCardIds(offer.getUserCards().stream().map(UserCard::getId).toList());
        offerDto.setWantedUserCardId(offer.getWantedUserCard().getId());
        return offerDto;
    }
}
