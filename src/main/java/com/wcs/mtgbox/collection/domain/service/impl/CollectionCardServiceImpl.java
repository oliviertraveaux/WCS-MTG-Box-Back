package com.wcs.mtgbox.collection.domain.service.impl;

import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import com.wcs.mtgbox.collection.domain.dto.AddCollectionCardDto;
import com.wcs.mtgbox.collection.domain.dto.CollectionCardDto;
import com.wcs.mtgbox.collection.domain.entity.Card;
import com.wcs.mtgbox.collection.domain.entity.CardLanguage;
import com.wcs.mtgbox.collection.domain.entity.CardQuality;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.collection.domain.service.CollectionCardService;
import com.wcs.mtgbox.collection.infrastructure.exception.*;
import com.wcs.mtgbox.collection.infrastructure.repository.CardLanguageRepository;
import com.wcs.mtgbox.collection.infrastructure.repository.CardQualityRepository;
import com.wcs.mtgbox.collection.infrastructure.repository.CardRepository;
import com.wcs.mtgbox.collection.infrastructure.repository.UserCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CollectionCardServiceImpl implements CollectionCardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final UserCardRepository userCardRepository;
    private final CardQualityRepository cardQualityRepository;
    private final CardLanguageRepository cardLanguageRepository;
    private final CardMapper cardMapper;

    public CollectionCardServiceImpl(
        CardRepository cardRepository,
        UserCardRepository userCardRepository,
        UserRepository userRepository,
        CardQualityRepository cardQualityRepository,
        CardLanguageRepository cardLanguageRepository,
        CardMapper cardMapper
    ) {
        this.cardRepository = cardRepository;
        this.userCardRepository = userCardRepository;
        this.userRepository = userRepository;
        this.cardQualityRepository = cardQualityRepository;
        this.cardLanguageRepository = cardLanguageRepository;
        this.cardMapper = cardMapper;
    }
    @Override
    @Transactional
    public List<CollectionCardDto> saveCollectionCards(List<AddCollectionCardDto> addCardList, Long tokenUserId) {

        List<CollectionCardDto> newCollectionCards = new ArrayList<CollectionCardDto>();

        addCardList.forEach(card -> {
            Optional<Card> dbCard = Optional.ofNullable(cardRepository.findCardByApiCardId(card.getCardInfo().getApiCardId()));
            if (dbCard.isEmpty()) {
                dbCard = this.saveCardInDatabase(card);
            }

            Card cardInfo = cardRepository.findById(dbCard.get().getId())
                    .orElseThrow(CardNotFoundErrorException::new);

            UserCard userCard = this.saveUserCard(cardInfo, card, tokenUserId);

            CollectionCardDto collectionCard = cardMapper.userCardEntityToCollectionCardDto(userCard);

            newCollectionCards.add(collectionCard);
        });

        return newCollectionCards;
    }

    private Optional<Card> saveCardInDatabase(AddCollectionCardDto newCard) {
        return Optional.of(cardRepository.save(cardMapper.addCollectionCardDtoToCardEntity(newCard)));
    }

    private UserCard saveUserCard(Card cardInfo, AddCollectionCardDto addCardDto, Long tokenUserId) {
        if (!addCardDto.getUserInfo().getUserId().equals(tokenUserId)) {
            throw new UserNotAuthorizedErrorException();
        }
        User user = userRepository.findById(addCardDto.getUserInfo().getUserId())
                .orElseThrow(UserNotFoundErrorException::new);
        CardQuality cardQuality = cardQualityRepository.findByName(addCardDto.getUserInfo().getQualityName())
                .orElseThrow(CardQualityNotFoundErrorException::new);
        CardLanguage cardLanguage = cardLanguageRepository.findByName(addCardDto.getUserInfo().getLanguageName())
                .orElseThrow(CardLanguageNotFoundErrorException::new);

        return userCardRepository.save(cardMapper.addCollectionCardDtoToUserCardEntity(user, cardInfo, cardQuality, cardLanguage));
    }

    @Override
    public List<CollectionCardDto> getCollectionCardsByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundErrorException::new);
        List<UserCard> userCards = userCardRepository.findAllByUserId(userId).stream()
                .filter(UserCard::getIsActive)
                .toList();
        List<CollectionCardDto> collectionCards = new ArrayList<>();

        for (UserCard userCard : userCards) {
            CollectionCardDto dto = cardMapper.userCardEntityToCollectionCardDto(userCard);
            collectionCards.add(dto);
        }

        return collectionCards;
    }

    @Override
    public void removeUserCardId(Long userCardId, Long tokenUserId) {
        UserCard userCard = userCardRepository.findById(userCardId)
                .orElseThrow(UserCardNotFoundErrorException::new);
        if (!userCard.getUser().getId().equals(tokenUserId)) {
            throw new UserNotAuthorizedErrorException();
        }
        userCardRepository.delete(userCard);
    }

    @Override
    public void removeUserCardsByUserCardIds(List<Long> userCardIdList, Long tokenUserId) {
        List<UserCard> userCards = userCardRepository.findAllById(userCardIdList)
                .stream()
                .filter(userCard -> userCard.getUser().getId().equals(tokenUserId))
                .toList();
        if (userCards.isEmpty()) {
            throw new UserCardNotFoundErrorException();
        }
        else {
            userCardRepository.deleteAll(userCards);
        }
    }
}
