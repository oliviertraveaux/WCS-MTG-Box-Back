package com.wcs.mtgbox.collection.application;

import com.wcs.mtgbox.collection.domain.service.SearchApiCardForCollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Tag(name = "API Card management", description = "API to handle user's cards from MTG API")
@RestController
@RequestMapping("/api/v1/apicards")
public class ApiCardController {
    private final SearchApiCardForCollectionService searchApiCardForCollectionService;

    public ApiCardController(
            SearchApiCardForCollectionService searchApiCardForCollectionService
    ) {
        this.searchApiCardForCollectionService = searchApiCardForCollectionService;
    }

    @GetMapping
    @Operation(summary = "Get API cards", description = "Retrieves all the cards from the external API using the provided search parameters")

    List<?> readAllApiCards(@RequestParam(required = false) Map<String,String> allParams) throws Exception{
        return searchApiCardForCollectionService.getApiCardsAndFormat(allParams);
    }
}
