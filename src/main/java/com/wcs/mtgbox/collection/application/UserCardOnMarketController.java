package com.wcs.mtgbox.collection.application;

import com.wcs.mtgbox.collection.domain.dto.UserCardOnMarketSearchResultDto;
import com.wcs.mtgbox.collection.domain.service.UserCardOnMarketService;
import com.wcs.mtgbox.collection.infrastructure.exception.UserCardOnMarketErrorException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/marketcards")
public class UserCardOnMarketController {

    private final UserCardOnMarketService userCardOnMarketService;

    public UserCardOnMarketController(UserCardOnMarketService marketCardService) {
        this.userCardOnMarketService = marketCardService;
    }
    @GetMapping
    List<UserCardOnMarketSearchResultDto> readAllMarketCards(@RequestParam Map<String,String> allParams) {
        if (allParams.isEmpty()) {
            throw new UserCardOnMarketErrorException();
        }
        return userCardOnMarketService.getMarketCards(allParams);
    }
}
