package com.wcs.mtgbox.filters.domain.service;


import com.wcs.mtgbox.filters.domain.entity.CardRarity;
import com.wcs.mtgbox.filters.domain.entity.CardSet;
import com.wcs.mtgbox.filters.domain.entity.CardType;
import com.wcs.mtgbox.filters.domain.service.dto.*;

import java.util.List;

public interface FilterService {

    AllFilterDTO readAll();

    List<CardTypeDTO> readAllApiCardTypes();

    List<CardSetDTO> readAllApiCardSets();

    List<CardSet> saveAllApiCardSets();

    List<CardType> saveAllCardTypes();

    List<CardLanguageDTO> readAllLanguages();

    List<CardQualityDTO> readAllQualities();

    List<CardRarity> readAllRarities();

}
