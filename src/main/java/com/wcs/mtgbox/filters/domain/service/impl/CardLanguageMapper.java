package com.wcs.mtgbox.filters.domain.service.impl;

import com.wcs.mtgbox.collection.domain.entity.CardLanguage;
import com.wcs.mtgbox.filters.domain.service.dto.CardLanguageDTO;
import org.springframework.stereotype.Service;

@Service
public class CardLanguageMapper {
    public CardLanguageDTO fromCardLanguageToCardLanguageDto(CardLanguage cardLanguage) {
        return new CardLanguageDTO(cardLanguage.getId(), cardLanguage.getName());
    }
}
