package com.wcs.mtgbox.collection.application;

import com.wcs.mtgbox.collection.domain.service.SearchApiCardForCollectionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/apicards")
public class ApiCardController {
    private SearchApiCardForCollectionService searchApiCardForCollectionService;

    public ApiCardController(
            SearchApiCardForCollectionService searchApiCardForCollectionService
    ) {
        this.searchApiCardForCollectionService = searchApiCardForCollectionService;
    }

    @GetMapping
    List<?> readAllApiCards(@RequestParam(required = false) Map<String,String> allParams) throws Exception{
        return searchApiCardForCollectionService.getApiCardsAndFormat(allParams);
    }

    @GetMapping("/types")
    List<?> readAllApiCardTypes() throws Exception{
        return searchApiCardForCollectionService.getAllApiCardTypes();
    }

}
