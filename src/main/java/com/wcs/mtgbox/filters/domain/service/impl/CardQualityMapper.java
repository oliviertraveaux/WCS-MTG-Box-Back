package com.wcs.mtgbox.filters.domain.service.impl;

import com.wcs.mtgbox.collection.domain.entity.CardQuality;
import com.wcs.mtgbox.filters.domain.service.dto.CardQualityDTO;
import org.springframework.stereotype.Service;

@Service
public class CardQualityMapper {

    public CardQualityDTO fromCardQualityToCardQualityDto(CardQuality cardQuality) {
        return new CardQualityDTO(cardQuality.getId(), cardQuality.getName(), cardQuality.getAbbreviation());
    }
}
