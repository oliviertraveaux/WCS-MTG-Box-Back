package com.wcs.mtgbox.filters.domain.service.impl;

import com.wcs.mtgbox.collection.domain.entity.CardLanguage;
import com.wcs.mtgbox.collection.domain.entity.CardQuality;
import com.wcs.mtgbox.filters.domain.entity.CardRarity;
import com.wcs.mtgbox.filters.domain.entity.CardSet;
import com.wcs.mtgbox.filters.domain.entity.CardType;
import com.wcs.mtgbox.filters.domain.service.FilterService;
import com.wcs.mtgbox.filters.domain.service.dto.*;
import com.wcs.mtgbox.filters.infrastructure.repository.*;
import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.api.SetAPI;
import io.magicthegathering.javasdk.resource.MtgSet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterServiceImpl implements FilterService {

    private final CardSetMapper apiFilterSetsMapper;
    private final CardLanguageMapper cardLanguageMapper;
    private final CardQualityMapper cardQualityMapper;
    private final AllFiltersMapper allFiltersMapper;
    private final LanguageRepository languageRepository;
    private final QualityRepository qualityRepository;
    private final RarityRepository rarityRepository;
    private final CardSetRepository cardSetRepository;
    private final CardTypeMapper apiFilterTypesMapper;
    private final CardTypeRepository cardTypeRepository;

    public FilterServiceImpl(CardSetMapper apiFilterSetsMapper, CardLanguageMapper cardLanguageMapper, CardQualityMapper cardQualityMapper, AllFiltersMapper allFiltersMapper, LanguageRepository languageRepository, QualityRepository qualityRepository, RarityRepository rarityRepository, CardSetRepository cardSetRepository, CardTypeMapper apiFilterTypesMapper, CardTypeRepository cardTypeRepository) {
        this.apiFilterSetsMapper = apiFilterSetsMapper;
        this.cardLanguageMapper = cardLanguageMapper;
        this.cardQualityMapper = cardQualityMapper;
        this.allFiltersMapper = allFiltersMapper;
        this.languageRepository = languageRepository;
        this.qualityRepository = qualityRepository;
        this.rarityRepository = rarityRepository;
        this.cardSetRepository = cardSetRepository;
        this.apiFilterTypesMapper = apiFilterTypesMapper;
        this.cardTypeRepository = cardTypeRepository;
    }

    @Override
    public AllFilterDTO readAll() {
        List<CardTypeDTO> types = readAllApiCardTypes();
        List<CardSetDTO> sets = readAllApiCardSets();
        List<CardLanguageDTO> languages = readAllLanguages();
        List<CardQualityDTO> qualities = readAllQualities();
        List<CardRarity> rarities = readAllRarities();
        return allFiltersMapper.fromIndividualFiltersToAllFilterDto(types, qualities, rarities, languages, sets);
    }

    @Override
    public List<CardTypeDTO> readAllApiCardTypes() {
        List<CardType> cardTypes = cardTypeRepository.findAll();
        return cardTypes.stream().map(apiFilterTypesMapper::fromCardTypeToCArdTypeDTO).toList();
    }

    @Override
    public List<CardType> saveAllCardTypes() {
        List<String> apiTypes = CardAPI.getAllCardTypes();
        List<CardType> cardTypes = apiTypes.stream().map(apiFilterTypesMapper::fromStringToCardType).toList();
        cardTypes.stream().map(cardTypeRepository::save).toList();
        return cardTypeRepository.findAll();
    }

    @Override
    public List<CardSetDTO> readAllApiCardSets() {
        List<CardSet> cardSets = this.cardSetRepository.findAll();
        return cardSets.stream().map(apiFilterSetsMapper::fromCardSetToApiSetDTO).toList();
    }

    @Override
    public List<CardSet> saveAllApiCardSets() {
        List<MtgSet> apiSets = SetAPI.getAllSets();
        List<CardSet> CardSets = apiSets.stream().map(apiFilterSetsMapper::fromMtgSetToCardSet).toList();
        CardSets.stream().map(cardSetRepository::save).toList();
        return cardSetRepository.findAll();
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