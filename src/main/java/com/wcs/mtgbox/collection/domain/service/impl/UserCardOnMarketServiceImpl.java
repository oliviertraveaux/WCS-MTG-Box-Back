package com.wcs.mtgbox.collection.domain.service.impl;

import com.wcs.mtgbox.collection.domain.dto.UserCardOnMarketSearchResultDto;
import com.wcs.mtgbox.collection.domain.entity.Card;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.collection.domain.service.UserCardOnMarketService;
import com.wcs.mtgbox.collection.infrastructure.exception.UserCardOnMarketErrorException;
import com.wcs.mtgbox.collection.infrastructure.repository.CardRepository;
import com.wcs.mtgbox.collection.infrastructure.repository.UserCardRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserCardOnMarketServiceImpl implements UserCardOnMarketService {
    private final CardRepository cardRepository;
    private final UserCardRepository userCardRepository;
    private final CardMapper cardMapper;


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
                && (allParams.containsKey("department") ? String.valueOf(userCard.getUser().getDepartment()).equals(allParams.get("department")) : true);
    }


    // This method build specification based on query params.
    // CardRepository must extend JpaSpecificationExecutor to make it work
    private List<Card> getCards(Map<String, String> allParams) {
        Specification<Card> spec = Specification.where(null);

        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "name":
                    spec = spec.and((root, query, cb) -> cb.like(root.get("name"), "%" + value + "%"));
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
                case "department":
                    break;
                default:
                    throw new UserCardOnMarketErrorException();
            }
        }
        return  cardRepository.findAll(spec);
    }
}
