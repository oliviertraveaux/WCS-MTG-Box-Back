package com.wcs.mtgbox.collection.domain.service.impl;

import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.collection.domain.dto.*;
import com.wcs.mtgbox.collection.domain.entity.Card;
import com.wcs.mtgbox.collection.domain.entity.CardLanguage;
import com.wcs.mtgbox.collection.domain.entity.CardQuality;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.collection.domain.model.CardBasicInfoWithId;
import com.wcs.mtgbox.collection.domain.model.CardUserInfo;
import com.wcs.mtgbox.collection.domain.model.OfferWantedCardUserInfo;
import com.wcs.mtgbox.collection.infrastructure.exception.UserCardNotFoundErrorException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public CollectionCardDto userCardEntityToCollectionCardDto(UserCard userCard) {
        CollectionCardDto cardDto = new CollectionCardDto(new CardBasicInfoWithId(), new CardUserInfo());
        Card cardInfo = userCard.getCard();

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
        userCardOnMarketSearchResultDto.setFrenchName(card.getFrenchName());
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

    public CardAdDto userCardToCardAdDto(Optional<UserCard> userCard) {
        if (userCard.isEmpty()){
            throw new UserCardNotFoundErrorException();
        }
        CardAdDto cardAdDto = new CardAdDto();
        userCard.ifPresent(
                userCardToReturn -> {
                    cardAdDto.setCardId(userCardToReturn.getCard().getId());
                    cardAdDto.setName(userCardToReturn.getCard().getName());
                    cardAdDto.setImageUrl(userCardToReturn.getCard().getImageUrl());
                    cardAdDto.setFrenchName(userCardToReturn.getCard().getFrenchName());
                    cardAdDto.setFrenchImageUrl(userCardToReturn.getCard().getFrenchImageUrl());
                    cardAdDto.setSetAbbreviation(userCardToReturn.getCard().getSetAbbreviation());
                    cardAdDto.setSetName(userCardToReturn.getCard().getSetName());
                    cardAdDto.setRarity(userCardToReturn.getCard().getRarity());
                    cardAdDto.setArtist(userCardToReturn.getCard().getArtist());
                    cardAdDto.setText(userCardToReturn.getCard().getText());
                    cardAdDto.setUserCard(this.userCardToUserCardOnMarketDto(userCardToReturn));
                }
        );
        return cardAdDto;
    }

    public OfferWantedCardDto userCardEntityToOfferWantedCardDto(UserCard userCard) {
        OfferWantedCardDto cardDto = new OfferWantedCardDto(new CardBasicInfoWithId(), new OfferWantedCardUserInfo());
        Card cardInfo = userCard.getCard();

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
        cardDto.getUserInfo().setUserName(userCard.getUser().getUsername());
        cardDto.getUserInfo().setCity(userCard.getUser().getCity());
        cardDto.getUserInfo().setDepartment(userCard.getUser().getDepartment());
        cardDto.getUserInfo().setQualityName(userCard.getCardQuality().getName());
        cardDto.getUserInfo().setLanguageName(userCard.getCardLanguage().getName());

        return cardDto;
    }
}
