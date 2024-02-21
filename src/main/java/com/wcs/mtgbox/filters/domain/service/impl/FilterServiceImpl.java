package com.wcs.mtgbox.filters.domain.service.impl;

import com.wcs.mtgbox.collection.domain.entity.CardLanguage;
import com.wcs.mtgbox.collection.domain.entity.CardQuality;
import com.wcs.mtgbox.filters.domain.entity.CardRarity;
import com.wcs.mtgbox.filters.domain.service.FilterService;
import com.wcs.mtgbox.filters.domain.service.dto.AllFilterDTO;
import com.wcs.mtgbox.filters.domain.service.dto.ApiSetDTO;
import com.wcs.mtgbox.filters.domain.service.dto.CardLanguageDTO;
import com.wcs.mtgbox.filters.domain.service.dto.CardQualityDTO;
import com.wcs.mtgbox.filters.infrastructure.repository.LanguageRepository;
import com.wcs.mtgbox.filters.infrastructure.repository.QualityRepository;
import com.wcs.mtgbox.filters.infrastructure.repository.RarityRepository;
import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.api.SetAPI;
import io.magicthegathering.javasdk.resource.MtgSet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterServiceImpl implements FilterService {

    private final ApiFilterSetsMapper apiFilterSetsMapper;
    private final CardLanguageMapper cardLanguageMapper;
    private final CardQualityMapper cardQualityMapper;
    private final AllFiltersMapper allFiltersMapper;
    private final LanguageRepository languageRepository;
    private final QualityRepository qualityRepository;
    private final RarityRepository rarityRepository;

    public FilterServiceImpl(ApiFilterSetsMapper apiFilterSetsMapper, CardLanguageMapper cardLanguageMapper, CardQualityMapper cardQualityMapper, AllFiltersMapper allFiltersMapper, LanguageRepository languageRepository, QualityRepository qualityRepository, RarityRepository rarityRepository) {
        this.apiFilterSetsMapper = apiFilterSetsMapper;
        this.cardLanguageMapper = cardLanguageMapper;
        this.cardQualityMapper = cardQualityMapper;
        this.allFiltersMapper = allFiltersMapper;
        this.languageRepository = languageRepository;
        this.qualityRepository = qualityRepository;
        this.rarityRepository = rarityRepository;
    }

    @Override
    public AllFilterDTO readAll() {
        List<String> types = readAllApiCardTypes();
        List<ApiSetDTO> sets = readAllApiCardSets();
        List<CardLanguageDTO> languages = readAllLanguages();
        List<CardQualityDTO> qualities = readAllQualities();
        List<CardRarity> rarities = readAllRarities();
        return allFiltersMapper.fromIndividualFiltersToAllFilterDto(types, qualities, rarities, languages, sets);
    }

    @Override
    public List<String> readAllApiCardTypes() {
        return CardAPI.getAllCardTypes();
    }

    @Override
    public List<ApiSetDTO> readAllApiCardSets() {
        List<MtgSet> apiSets = SetAPI.getAllSets();
        return apiSets.stream().map(apiFilterSetsMapper::fromMtgSetToApiSetDto).toList();
    }

    @Override
    public List<CardLanguageDTO> readAllLanguages() {
        List<CardLanguage> languages = languageRepository.findAll();
        return languages.stream().map(cardLanguageMapper::fromCardLanguageToCardLanguageDto).toList();
    }

    @Override
    public List<CardQualityDTO> readAllQualities() {
        List<CardQuality> qualities = qualityRepository.findAll();
        return qualities.stream().map(cardQualityMapper::fromCardQualityToCardQualityDto).toList();
    }

    @Override
    public List<CardRarity> readAllRarities() {
        return rarityRepository.findAll();
    }
}