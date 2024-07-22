package com.wcs.mtgbox.filters.domain.service.dto;

import com.wcs.mtgbox.filters.domain.entity.CardRarity;
import com.wcs.mtgbox.filters.domain.entity.CardType;
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

    private List<CardTypeDTO> types;
    private List<CardQualityDTO> qualities;
    private List<CardRarity> rarities;
    private List<CardLanguageDTO> languages;
    private List<CardSetDTO> sets;
}
