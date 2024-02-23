package com.wcs.mtgbox.filters.domain.service.impl;

import com.wcs.mtgbox.filters.domain.entity.CardType;
import com.wcs.mtgbox.filters.domain.service.dto.CardTypeDTO;
import org.springframework.stereotype.Service;

@Service
public class CardTypeMapper {
    public CardType fromStringToCardType(String mtgCardType) {
        CardType cardType = new CardType();
        cardType.setName(mtgCardType);
        return cardType;
    }

    public CardTypeDTO fromCardTypeToCArdTypeDTO(CardType cardType) {
        return new CardTypeDTO(cardType.getName());
    }
}
