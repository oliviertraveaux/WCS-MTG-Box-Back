package com.wcs.mtgbox.collection.domain.service;

import com.wcs.mtgbox.collection.domain.dto.ApiCardDTO;
import io.magicthegathering.javasdk.resource.Card;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchApiCardForCollectionService {
    private GetApiCardService getApiCardService;
    private ApiCardMapper apiCardMapper;

    public SearchApiCardForCollectionService(
            GetApiCardService getApiCardService,
            ApiCardMapper apiCardMapper
    ) {
        this.getApiCardService = getApiCardService;
        this.apiCardMapper = apiCardMapper;
    }
    public List<ApiCardDTO> getApiCardsAndFormat(Map<String,String> filtersList){
       List<Card> apiCards = getApiCardService.getCardsFromApi(filtersList);
       List<ApiCardDTO> cards = new ArrayList<ApiCardDTO>();
       apiCards.forEach((card) -> {
           if (card.getImageUrl() != null){
               cards.add(apiCardMapper.transformApiCardRawInApiCardDto(card));
           }
       });
       return cards;
    }

    public List<String> getAllApiCardTypes(){
        return getApiCardService.getCardsTypeFromApi();
    }
}
