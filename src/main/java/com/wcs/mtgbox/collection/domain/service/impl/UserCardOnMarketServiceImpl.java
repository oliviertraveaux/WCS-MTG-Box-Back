package com.wcs.mtgbox.collection.domain.service.impl;

import com.wcs.mtgbox.collection.domain.dto.UserCardOnMarketSearchResultDto;
import com.wcs.mtgbox.collection.domain.entity.Card;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.collection.domain.service.UserCardOnMarketService;
import com.wcs.mtgbox.collection.infrastructure.exception.UserCardOnMarketErrorException;
import com.wcs.mtgbox.collection.infrastructure.repository.CardRepository;
import com.wcs.mtgbox.collection.infrastructure.repository.UserCardRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserCardOnMarketServiceImpl implements UserCardOnMarketService {
    private final CardRepository cardRepository;
    private final UserCardRepository userCardRepository;
    private final CardMapper cardMapper;

    @Value("${lastConnexionDelay:15}")
    private int LAST_CONNEXION_DELAY;


    public UserCardOnMarketServiceImpl(CardRepository cardRepository, UserCardRepository userCardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.userCardRepository = userCardRepository;
        this.cardMapper = cardMapper;
    }

    @Override
    public List<UserCardOnMarketSearchResultDto> getMarketCards(Map<String, String> allParams) {

        List<Card> cards = this.getCards(allParams);
        return this.fromCardsToUserCardOnMarketSearchResults(cards, allParams);
    }



    @Override
    public List<UserCardOnMarketSearchResultDto> getLastMarketCards(int limit) {
        List<Card> lastMarketCards = this.getLastCards(limit);
        return this.fromCardsToUserCardOnMarketSearchResults(lastMarketCards, new HashMap<>());
    }

    private List<Card> getLastCards(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("id").descending());
        return cardRepository.findAll(pageable).getContent();
    }



    // return searchResult if userCard list is not empty and valid
    private List<UserCardOnMarketSearchResultDto> fromCardsToUserCardOnMarketSearchResults(List<Card> cards, Map<String, String> allParams) {
        List<UserCardOnMarketSearchResultDto> marketCardSearchResultDtos = new ArrayList<>();

        for (Card card : cards) {
            List<UserCard> userCards = userCardRepository.findAllByCardId(card.getId());
            if (!userCards.isEmpty()) {
                List<UserCard> validUserCards = userCards.stream()
                        .filter(userCard -> isValidUserCard(userCard, allParams))
                        .toList();
                if (!validUserCards.isEmpty()) {
                    UserCardOnMarketSearchResultDto marketCardSearchResultDto = cardMapper.userCardToUserCardOnMarketSearchResultDto(card, validUserCards);
                    marketCardSearchResultDtos.add(marketCardSearchResultDto);
                }
            }
        };

        return marketCardSearchResultDtos;
    }

    private boolean isValidUserCard(UserCard userCard, Map<String, String> allParams) {
        return userCard.getIsActive()
                && userCard.getUser().getIsActive()
                && !userCard.getUser().getIsBanned()
                && (!allParams.containsKey("location") || String.valueOf(userCard.getUser().getDepartment()).equals(allParams.get("location")))
                && (!allParams.containsKey("language") || String.valueOf(userCard.getCardLanguage().getName()).equals(allParams.get("language")))
                && (!allParams.containsKey("recentlyConnected") || isUserConnectedRecently(userCard.getUser().getLastConnectionDate()));
    }

    private boolean isUserConnectedRecently(LocalDateTime lastConnectionDate) {
        return lastConnectionDate.isAfter(LocalDateTime.now().minusDays(LAST_CONNEXION_DELAY));
    }

    // This method build specification based on query params.
    // CardRepository must extend JpaSpecificationExecutor to make it work
    private List<Card> getCards(Map<String, String> allParams) {
        Specification<Card> spec = Specification.where(null);

        String cardName;

        if (allParams.containsKey("language") && allParams.get("language").equals("French")) {
            cardName = "frenchName";
        } else {
            cardName = "name";
        }

        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "name":
                    spec = spec.and((root, query, cb) -> cb.like(root.get(cardName), "%" + value + "%"));
                    break;
                case "cmc":
                    int cost = Integer.parseInt(value);
                    spec = spec.and((root, query, cb) -> cb.equal(root.get("manaCost"), cost));
                    break;
                case "artist":
                    spec = spec.and((root, query, cb) -> cb.like(root.get("artist"), "%" + value + "%"));
                    break;
                case "rarity":
                    spec = spec.and((root, query, cb) -> cb.equal(root.get("rarity"), value));
                    break;
                case "type":
                    spec = spec.and((root, query, cb) -> cb.equal(root.get("type"), value));
                    break;
                case "text":
                    spec = spec.and((root, query, cb) -> cb.like(root.get("text"), "%" + value + "%"));
                    break;
                case "set":
                    spec = spec.and((root, query, cb) -> cb.equal(root.get("setAbbreviation"), value));
                    break;
                case "location", "language", "recentlyConnected":
                    break;
                default:
                    throw new UserCardOnMarketErrorException();
            }
        }
        return  cardRepository.findAll(spec);
    }

}
