package com.wcs.mtgbox.filters.domain.service;


import com.wcs.mtgbox.collection.domain.entity.CardQuality;
import com.wcs.mtgbox.filters.domain.entity.CardRarity;
import com.wcs.mtgbox.filters.domain.service.dto.AllFilterDTO;
import com.wcs.mtgbox.filters.domain.service.dto.ApiSetDTO;
import com.wcs.mtgbox.filters.domain.service.dto.CardLanguageDTO;
import com.wcs.mtgbox.filters.domain.service.dto.CardQualityDTO;

import java.util.List;

public interface FilterService {

    AllFilterDTO readAll();

    List<String> readAllApiCardTypes();

    List<ApiSetDTO> readAllApiCardSets();

    List<CardLanguageDTO> readAllLanguages();

    List<CardQualityDTO> readAllQualities();

    List<CardRarity> readAllRarities();

}
