package com.wcs.mtgbox.filters.application;

import com.wcs.mtgbox.filters.domain.entity.CardRarity;
import com.wcs.mtgbox.filters.domain.entity.CardSet;
import com.wcs.mtgbox.filters.domain.entity.CardType;
import com.wcs.mtgbox.filters.domain.service.FilterService;
import com.wcs.mtgbox.filters.domain.service.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Filter management", description = "API to handle user's filters for cards and collections")
@RestController
@RequestMapping("/api/v1/filters")
public class FilterController {

    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping
    @Operation(summary = "Get all filters", description = "Retrieves all the available filters for cards and collections")

    public ResponseEntity<AllFilterDTO> readAll() {
        AllFilterDTO allFilterDTO = filterService.readAll();
        return ResponseEntity.ok().body(allFilterDTO);
    }

    @GetMapping("/sets")
    @Operation(summary = "Get all card sets", description = "Retrieves all the available card sets for cards and collections")

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
    @Operation(summary = "Get all card types", description = "Retrieves all the available card types for cards and collections")

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
    @Operation(summary = "Get all languages", description = "Retrieves all the available languages for cards and collections")

    public ResponseEntity<List<CardLanguageDTO>> readAllLanguages() {
        List<CardLanguageDTO> languages = filterService.readAllLanguages();
        return ResponseEntity.ok().body(languages);
    }

    @GetMapping("/qualities")
    @Operation(summary = "Get all qualities", description = "Retrieves all the available qualities for cards and collections")

    public ResponseEntity<List<CardQualityDTO>> readAllQualities() {
        List<CardQualityDTO> qualities = filterService.readAllQualities();
        return ResponseEntity.ok().body(qualities);
    }

    @GetMapping("/rarities")
    @Operation(summary = "Get all rarities", description = "Retrieves all the available rarities for cards and collections")

    public ResponseEntity<List<CardRarity>> readAllRarities() {
        List<CardRarity> rarities = filterService.readAllRarities();
        return ResponseEntity.ok().body(rarities);
    }
}
