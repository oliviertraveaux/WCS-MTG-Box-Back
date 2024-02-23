package com.wcs.mtgbox.filters.domain.service.impl;

import com.wcs.mtgbox.filters.domain.entity.CardRarity;
import com.wcs.mtgbox.filters.domain.entity.CardType;
import com.wcs.mtgbox.filters.domain.service.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllFiltersMapper {

    public AllFilterDTO fromIndividualFiltersToAllFilterDto(List<CardTypeDTO> types, List<CardQualityDTO> qualities, List<CardRarity> rarities, List<CardLanguageDTO> languages, List<CardSetDTO> sets) {
        return new AllFilterDTO(types, qualities, rarities, languages, sets);
    }

}
