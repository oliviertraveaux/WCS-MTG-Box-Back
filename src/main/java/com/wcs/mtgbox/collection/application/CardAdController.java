package com.wcs.mtgbox.collection.application;

import com.wcs.mtgbox.collection.domain.service.CardAdService;
import com.wcs.mtgbox.collection.infrastructure.exception.UserCardNotFoundErrorException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Card Ad management", description = "API to handle user's cards for trading")
@RestController
@RequestMapping("/api/v1/card-ad")
public class CardAdController {
    private final CardAdService cardAdService;

    public CardAdController(CardAdService cardAdService) {
        this.cardAdService = cardAdService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a card ad", description = "Retrieves a card ad by its ID")
    ResponseEntity<?> getCardAd(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cardAdService.getCardAd(id));
        } catch (Exception e) {
            throw new UserCardNotFoundErrorException(id);
        }
    }
}
