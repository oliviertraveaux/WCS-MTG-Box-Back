package com.wcs.mtgbox.filters.application;

import com.wcs.mtgbox.filters.domain.entity.CardRarity;
import com.wcs.mtgbox.filters.domain.entity.CardSet;
import com.wcs.mtgbox.filters.domain.entity.CardType;
import com.wcs.mtgbox.filters.domain.service.FilterService;
import com.wcs.mtgbox.filters.domain.service.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/filters")
public class FilterController {

    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping
    public ResponseEntity<AllFilterDTO> readAll() {
        AllFilterDTO allFilterDTO = filterService.readAll();
        return ResponseEntity.ok().body(allFilterDTO);
    }

    @GetMapping("/sets")
    public ResponseEntity<List<CardSetDTO>> readAllCardsSets() {
        List<CardSetDTO> apiSets = filterService.readAllApiCardSets();
        return ResponseEntity.ok().body(apiSets);
    }

    // UNCOMMENT FOR DEVELOPMENT PURPOSE ONLY. REMOVE FOR PRODUCTION

//    @PostMapping("/sets")
//    public ResponseEntity<List<CardSet>> saveAllApiCardSets() {
//        List<CardSet> apiSets = filterService.saveAllApiCardSets();
//        return ResponseEntity.ok().body(apiSets);
//    }

    @GetMapping("/types")
    public ResponseEntity<List<CardTypeDTO>> readAllCardTypes() {
        List<CardTypeDTO> cardTypes = filterService.readAllApiCardTypes();
        return ResponseEntity.ok().body(cardTypes);
    }

    // UNCOMMENT FOR DEVELOPMENT PURPOSE ONLY. REMOVE FOR PRODUCTION

//    @PostMapping("/types")
//    public ResponseEntity<List<CardType>> saveAllCardTypes() {
//        List<CardType> cardTypes = filterService.saveAllCardTypes();
//        return ResponseEntity.ok().body(cardTypes);
//    }

    @GetMapping("/languages")
    public ResponseEntity<List<CardLanguageDTO>> readAllLanguages() {
        List<CardLanguageDTO> languages = filterService.readAllLanguages();
        return ResponseEntity.ok().body(languages);
    }

    @GetMapping("/qualities")
    public ResponseEntity<List<CardQualityDTO>> readAllQualities() {
        List<CardQualityDTO> qualities = filterService.readAllQualities();
        return ResponseEntity.ok().body(qualities);
    }

    @GetMapping("/rarities")
    public ResponseEntity<List<CardRarity>> readAllRarities() {
        List<CardRarity> rarities = filterService.readAllRarities();
        return ResponseEntity.ok().body(rarities);
    }
}
