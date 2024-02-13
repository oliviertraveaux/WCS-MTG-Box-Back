package com.wcs.mtgbox.collection.domain.service.impl;

import com.wcs.mtgbox.collection.domain.service.GetApiCardService;
import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetApiCardServiceImpl implements GetApiCardService {
    @Override
    public Card getCardFromApi(int multiverseId) {
        return CardAPI.getCard(multiverseId);
    }

    @Override
    public Card getCardFromApi(String cardId) {
        return CardAPI.getCard(cardId);
    }

    @Override
    public List<Card> getCardsFromApi(Map<String,String> filtersList) {
        String filters = String.valueOf(filtersList);
        filters = filters.substring(1, filters.length() - 1);
        List<String> myList = new ArrayList<String>(Arrays.asList(filters.split(", ")));
        return CardAPI.getAllCards(myList);
    }

    @Override
    public List<String> getCardsTypeFromApi() {
        return CardAPI.getAllCardTypes();
    }

    @Override
    public List<String> getCardsSuperTypeFromApi() {
        return CardAPI.getAllCardSupertypes();
    }

    @Override
    public List<String> getCardsSubTypeFromApi() {
        return CardAPI.getAllCardSubtypes();
    }
}
