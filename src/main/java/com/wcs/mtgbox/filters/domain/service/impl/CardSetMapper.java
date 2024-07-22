package com.wcs.mtgbox.filters.domain.service.impl;

import com.wcs.mtgbox.filters.domain.entity.CardSet;
import com.wcs.mtgbox.filters.domain.service.dto.CardSetDTO;
import io.magicthegathering.javasdk.resource.MtgSet;
import org.springframework.stereotype.Service;

@Service
public class CardSetMapper {
    public CardSet fromMtgSetToCardSet(MtgSet mtgSet) {
        CardSet cardSet = new CardSet();
        cardSet.setName(mtgSet.getName());
        cardSet.setCode(mtgSet.getCode());
        return cardSet;
    }

    public CardSetDTO fromCardSetToApiSetDTO(CardSet cardSet) {
        return new CardSetDTO(cardSet.getName(), cardSet.getCode());
    }
}