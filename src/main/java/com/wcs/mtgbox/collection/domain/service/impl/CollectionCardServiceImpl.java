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
import com.wcs.mtgbox.collection.infrastructure.exception.CardLanguageNotFoundErrorException;
import com.wcs.mtgbox.collection.infrastructure.exception.CardNotFoundErrorException;
import com.wcs.mtgbox.collection.infrastructure.exception.CardQualityNotFoundErrorException;
import com.wcs.mtgbox.collection.infrastructure.repository.CardLanguageRepository;
import com.wcs.mtgbox.collection.infrastructure.repository.CardQualityRepository;
import com.wcs.mtgbox.collection.infrastructure.repository.CardRepository;
import com.wcs.mtgbox.collection.infrastructure.repository.UserCardRepository;
import org.springframework.stereotype.Service;

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
    public List<CollectionCardDto> saveCollectionCards(List<AddCollectionCardDto> addCardList) {

        List<CollectionCardDto> newCollectionCards = new ArrayList<CollectionCardDto>();

        addCardList.forEach((card) -> {
            Optional<Card> dbCard = Optional.ofNullable(cardRepository.findCardByApiCardId(card.getCardInfo().getApiCardId()));
            if (dbCard.isEmpty()) {
                dbCard = this.saveCardInDatabase(card);
            }

            Card cardInfo = cardRepository.findById(dbCard.get().getId())
                    .orElseThrow(CardNotFoundErrorException::new);

            UserCard userCard = this.saveUserCard(cardInfo,card);

            CollectionCardDto collectionCard = cardMapper.cardAndUserCardEntityToCollectionCardDtoTo(cardInfo, userCard);

            newCollectionCards.add(collectionCard);
        });

        return newCollectionCards;
    }

    private Optional<Card> saveCardInDatabase(AddCollectionCardDto newCard) {
        return Optional.of(cardRepository.save(cardMapper.addCollectionCardDtoToCardEntity(newCard)));
    }

    private UserCard saveUserCard(Card cardInfo, AddCollectionCardDto addCardDto) {
        User user = userRepository.findById(addCardDto.getUserInfo().getUserId())
                .orElseThrow(UserNotFoundErrorException::new);
        CardQuality cardQuality = cardQualityRepository.findById(addCardDto.getUserInfo().getQualityId())
                .orElseThrow(CardQualityNotFoundErrorException::new);
        CardLanguage cardLanguage = cardLanguageRepository.findById(addCardDto.getUserInfo().getLanguageId())
                .orElseThrow(CardLanguageNotFoundErrorException::new);

        return userCardRepository.save(cardMapper.addCollectionCardDtoToUserCardEntity(user, cardInfo, cardQuality, cardLanguage));
    }

    @Override
    public List<CollectionCardDto> getCollectionCardsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundErrorException::new);
        List<UserCard> userCards = userCardRepository.findAllByUserId(userId);
        if (userCards.isEmpty()) {
            throw new CardNotFoundErrorException(userId);

        }
        List<CollectionCardDto> collectionCards = new ArrayList<>();

        for (UserCard userCard : userCards) {
            Card card = userCard.getCard();
            CollectionCardDto dto = cardMapper.cardAndUserCardEntityToCollectionCardDtoTo(card, userCard);
            collectionCards.add(dto);
        }

        return collectionCards;
    }



}
