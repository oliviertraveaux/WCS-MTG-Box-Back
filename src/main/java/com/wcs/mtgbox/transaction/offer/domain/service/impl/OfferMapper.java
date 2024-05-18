package com.wcs.mtgbox.transaction.offer.domain.service.impl;

import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.collection.domain.dto.CollectionCardDto;
import com.wcs.mtgbox.collection.domain.entity.Card;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.collection.domain.service.impl.CardMapper;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferCreationDto;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferDto;
import com.wcs.mtgbox.transaction.offer.domain.entity.Offer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfferMapper {
    private final CardMapper cardMapper;
     public OfferMapper(CardMapper cardMapper){
         this.cardMapper = cardMapper;
     }

    public Offer offerCreationDtoToOfferEntity(User user, List<UserCard> userCards, UserCard wantedUserCard) {
        Offer offer = new Offer();
        offer.setWantedUserCard(wantedUserCard);
        offer.setUser(user);
        offer.setUserCards(userCards);
        offer.setStatus("PENDING");
        offer.setCreatedDate(LocalDateTime.now());
        return offer;
    }

    public OfferCreationDto offerEntityToOfferCreationDto(Offer offer) {
        OfferCreationDto offerCreationDto = new OfferCreationDto();
        offerCreationDto.setUserId(offer.getUser().getId());
        offerCreationDto.setUserCardIds(offer.getUserCards().stream().map(UserCard::getId).toList());
        offerCreationDto.setWantedUserCardId(offer.getWantedUserCard().getId());
        return offerCreationDto;
    }

    public OfferDto offerEntityToOfferDto(Offer offer){
        OfferDto offerDto = new OfferDto();
        offerDto.setId(offer.getId());
        offerDto.setWantedUserCardId(offer.getWantedUserCard().getId());
        offerDto.setUserId(offer.getUser().getId());
        offerDto.setUserName(offer.getUser().getUsername());
        offerDto.setCity(offer.getUser().getCity());
        offerDto.setDepartment(offer.getUser().getDepartment());
        offerDto.setStatus(offer.getStatus());
        offerDto.setCreatedDate(offer.getCreatedDate());
        offerDto.setAcceptedDate(offer.getAcceptedDate());

        List<CollectionCardDto> collectionCards = new ArrayList<>();
        offer.getUserCards().forEach(userCard -> {
            Card card = userCard.getCard();
            CollectionCardDto collectionCardDto = cardMapper.cardAndUserCardEntityToCollectionCardDtoTo(card, userCard);
            collectionCards.add(collectionCardDto);
        });
        offerDto.setUserCards(collectionCards);
        return offerDto;
    }
}
