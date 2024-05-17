package com.wcs.mtgbox.collection.application;

import com.wcs.mtgbox.collection.domain.service.CardAdService;
import com.wcs.mtgbox.collection.infrastructure.exception.UserCardNotFoundErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/card-ad")
public class CardAdController {
    private final CardAdService cardAdService;

    public CardAdController(CardAdService cardAdService) {
        this.cardAdService = cardAdService;
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getCardAd(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cardAdService.getCardAd(id));
        } catch (Exception e) {
            throw new UserCardNotFoundErrorException(id);
        }
    }
}
