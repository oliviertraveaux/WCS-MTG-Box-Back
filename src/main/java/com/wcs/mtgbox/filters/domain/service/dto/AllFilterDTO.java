package com.wcs.mtgbox.filters.domain.service.dto;

import com.wcs.mtgbox.collection.domain.entity.CardLanguage;
import com.wcs.mtgbox.collection.domain.entity.CardQuality;
import com.wcs.mtgbox.filters.domain.entity.CardRarity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllFilterDTO {

    private List<String> types;
    private List<CardQualityDTO> qualities;
    private List<CardRarity> rarities;
    private List<CardLanguageDTO> languages;
    private List<ApiSetDTO> sets;
}
