package com.wcs.mtgbox.filters.domain.service.impl;

import com.wcs.mtgbox.filters.domain.entity.CardRarity;
import com.wcs.mtgbox.filters.domain.service.dto.AllFilterDTO;
import com.wcs.mtgbox.filters.domain.service.dto.ApiSetDTO;
import com.wcs.mtgbox.filters.domain.service.dto.CardLanguageDTO;
import com.wcs.mtgbox.filters.domain.service.dto.CardQualityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllFiltersMapper {

    public AllFilterDTO fromIndividualFiltersToAllFilterDto(List<String> types, List<CardQualityDTO> qualities, List<CardRarity> rarities, List<CardLanguageDTO> languages, List<ApiSetDTO> sets) {
        return new AllFilterDTO(types, qualities, rarities, languages, sets);
    }

}
