package com.wcs.mtgbox.collection.domain.service.impl;

import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.collection.domain.dto.AddCollectionCardDto;
import com.wcs.mtgbox.collection.domain.dto.CollectionCardDto;
import com.wcs.mtgbox.collection.domain.dto.UserCardOnMarketSearchResultDto;
import com.wcs.mtgbox.collection.domain.dto.UserCardOnMarketDto;
import com.wcs.mtgbox.collection.domain.entity.Card;
import com.wcs.mtgbox.collection.domain.entity.CardLanguage;
import com.wcs.mtgbox.collection.domain.entity.CardQuality;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.collection.domain.model.CardBasicInfoWithId;
import com.wcs.mtgbox.collection.domain.model.CardUserInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardMapper {

    public Card addCollectionCardDtoToCardEntity(AddCollectionCardDto cardDto) {
        Card card = new Card();
        card.setApiCardId(cardDto.getCardInfo().getApiCardId());
        card.setName(cardDto.getCardInfo().getName());
        card.setImageUrl(cardDto.getCardInfo().getImageUrl());
        card.setFrenchName(cardDto.getCardInfo().getFrenchName());
        card.setFrenchImageUrl(cardDto.getCardInfo().getFrenchImageUrl());
        card.setManaCost(cardDto.getCardInfo().getManaCost());
        card.setRarity(cardDto.getCardInfo().getRarity());
        card.setSetAbbreviation(cardDto.getCardInfo().getSetAbbreviation());
        card.setSetName(cardDto.getCardInfo().getSetName());
        card.setText(cardDto.getCardInfo().getText());
        card.setArtist(cardDto.getCardInfo().getArtist());

        return card;
    }

    public UserCard addCollectionCardDtoToUserCardEntity(User user, Card cardInfo, CardQuality quality, CardLanguage language) {
        UserCard userCard = new UserCard();
        userCard.setUser(user);
        userCard.setCard(cardInfo);
        userCard.setCardQuality(quality);
        userCard.setCardLanguage(language);
        userCard.setIsActive(true);

        return userCard;
    }

    public CollectionCardDto cardAndUserCardEntityToCollectionCardDtoTo(Card cardInfo, UserCard userCard) {
        CollectionCardDto cardDto = new CollectionCardDto(new CardBasicInfoWithId(), new CardUserInfo());

        cardDto.getCardInfo().setUniqueId(cardInfo.getId());
        cardDto.getCardInfo().setApiCardId(cardInfo.getApiCardId());
        cardDto.getCardInfo().setName(cardInfo.getName());
        cardDto.getCardInfo().setImageUrl(cardInfo.getImageUrl());
        cardDto.getCardInfo().setFrenchName(cardInfo.getFrenchName());
        cardDto.getCardInfo().setFrenchImageUrl(cardInfo.getFrenchImageUrl());
        cardDto.getCardInfo().setManaCost(cardInfo.getManaCost());
        cardDto.getCardInfo().setRarity(cardInfo.getRarity());
        cardDto.getCardInfo().setSetAbbreviation(cardInfo.getSetAbbreviation());
        cardDto.getCardInfo().setSetName(cardInfo.getSetName());
        cardDto.getCardInfo().setText(cardInfo.getText());
        cardDto.getCardInfo().setArtist(cardInfo.getArtist());

        cardDto.getUserInfo().setUserCardId(userCard.getId());
        cardDto.getUserInfo().setUserId(userCard.getUser().getId());
        cardDto.getUserInfo().setQualityName(userCard.getCardQuality().getName());
        cardDto.getUserInfo().setLanguageName(userCard.getCardLanguage().getName());

        return cardDto;
    }

    public UserCardOnMarketSearchResultDto userCardToUserCardOnMarketSearchResultDto(Card card, List<UserCard> userCards) {

        List<UserCardOnMarketDto> userCardsOnMarket = new ArrayList<>();
        for (UserCard userCard : userCards) {
            userCardsOnMarket.add(userCardToUserCardOnMarketDto(userCard));
        }

        UserCardOnMarketSearchResultDto userCardOnMarketSearchResultDto = new UserCardOnMarketSearchResultDto();
        userCardOnMarketSearchResultDto.setCardId(card.getId());
        userCardOnMarketSearchResultDto.setName(card.getName());
        userCardOnMarketSearchResultDto.setImageUrl(card.getImageUrl());
        userCardOnMarketSearchResultDto.setFrenchImageUrl(card.getFrenchImageUrl());
        userCardOnMarketSearchResultDto.setSetAbbreviation(card.getSetAbbreviation());
        userCardOnMarketSearchResultDto.setSetName(card.getSetName());
        userCardOnMarketSearchResultDto.setRarity(card.getRarity());
        userCardOnMarketSearchResultDto.setArtist(card.getArtist());
        userCardOnMarketSearchResultDto.setText(card.getText());
        userCardOnMarketSearchResultDto.setUserCardsOnMarket(userCardsOnMarket);

        return userCardOnMarketSearchResultDto;
    }

    public UserCardOnMarketDto userCardToUserCardOnMarketDto(UserCard userCard) {
        UserCardOnMarketDto userCardOnMarketDto = new UserCardOnMarketDto();
        userCardOnMarketDto.setUserCardId(userCard.getId());
        userCardOnMarketDto.setUserId(userCard.getUser().getId());
        userCardOnMarketDto.setUserName(userCard.getUser().getUsername());
        userCardOnMarketDto.setCity(userCard.getUser().getCity());
        userCardOnMarketDto.setQuality(userCard.getCardQuality().getName());
        userCardOnMarketDto.setLanguage(userCard.getCardLanguage().getName());
        userCardOnMarketDto.setDepartment(userCard.getUser().getDepartment());
        // TODO (set to false by default for now)
        userCardOnMarketDto.setHasAnOffer(false);

        return userCardOnMarketDto;
    }
}
