package com.wcs.mtgbox.collection.domain.service.impl;

import com.wcs.mtgbox.collection.domain.dto.ApiCardDTO;
import com.wcs.mtgbox.collection.domain.service.GetApiCardService;
import com.wcs.mtgbox.collection.domain.service.SearchApiCardForCollectionService;
import io.magicthegathering.javasdk.resource.Card;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SearchApiCardForCollectionServiceImpl implements SearchApiCardForCollectionService {
    private final GetApiCardService getApiCardService;
    private final ApiCardMapper apiCardMapper;

    public SearchApiCardForCollectionServiceImpl(
            GetApiCardServiceImpl getApiCardServiceImpl,
            ApiCardMapper apiCardMapper
    ) {
        this.getApiCardService = getApiCardServiceImpl;
        this.apiCardMapper = apiCardMapper;
    }

    @Override
    public List<ApiCardDTO> getApiCardsAndFormat(Map<String,String> filtersList){
       List<Card> apiCards = getApiCardService.getCardsFromApi(filtersList);
       List<ApiCardDTO> cards = new ArrayList<ApiCardDTO>();

       if (filtersList.get("language") != null && filtersList.get("language").equals("french")){
           apiCards.forEach((card) -> {
                   Object[] result =  Arrays.stream(card.getForeignNames())
                           .filter((foreignData) -> (
                                   foreignData.getLanguage().equals("French") && foreignData.getImageUrl() != null
                           )).toArray();
                   if ( result.length > 0 ) {
                       cards.add(apiCardMapper.fromApiCardRawToApiCardDto(card));
                   }
           });
       } else {
           apiCards.forEach((card) -> {
               if (card.getImageUrl() != null){
                   cards.add(apiCardMapper.fromApiCardRawToApiCardDto(card));
               }
           });
       }
       return cards;
    }

    @Override
    public List<String> getAllApiCardTypes(){
        return getApiCardService.getCardsTypeFromApi();
    }
}
