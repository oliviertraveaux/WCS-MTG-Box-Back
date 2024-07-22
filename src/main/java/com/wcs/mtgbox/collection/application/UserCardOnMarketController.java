package com.wcs.mtgbox.collection.application;

import com.wcs.mtgbox.collection.domain.dto.UserCardOnMarketSearchResultDto;
import com.wcs.mtgbox.collection.domain.service.UserCardOnMarketService;
import com.wcs.mtgbox.collection.infrastructure.exception.UserCardOnMarketErrorException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Market management", description = "API to handle user's cards on the market")
@RestController
@RequestMapping("/api/v1/marketcards")
public class UserCardOnMarketController {

    private final UserCardOnMarketService userCardOnMarketService;

    public UserCardOnMarketController(UserCardOnMarketService marketCardService) {
        this.userCardOnMarketService = marketCardService;
    }
    @GetMapping
    @Operation(summary = "Get all market cards", description = "Retrieves all the cards on the market using the provided search parameters")
    List<UserCardOnMarketSearchResultDto> readAllMarketCards(@RequestParam Map<String,String> allParams) {
        if (allParams.isEmpty()) {
            throw new UserCardOnMarketErrorException();
        }
        return userCardOnMarketService.getMarketCards(allParams);
    }

    @GetMapping("/latest")
    @Operation(summary = "Get last market cards", description = "Retrieves the last cards added to the market")
    public List<UserCardOnMarketSearchResultDto> getLastMarketCards(@RequestParam int limit) {
        return userCardOnMarketService.getLastMarketCards(limit);
    }
}
