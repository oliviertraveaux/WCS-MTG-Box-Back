package com.wcs.mtgbox.collection.domain.service;

import io.magicthegathering.javasdk.resource.Card;

import java.util.List;
import java.util.Map;

public interface GetApiCardService {

    Card getCardFromApi(int multiverseId);

    Card getCardFromApi(String cardId);

    List<Card> getCardsFromApi(Map<String,String> filtersList);

    List<String> getCardsTypeFromApi();

    List<String> getCardsSuperTypeFromApi();

    List<String> getCardsSubTypeFromApi();
}
