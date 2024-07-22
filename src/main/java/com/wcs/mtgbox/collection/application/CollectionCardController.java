package com.wcs.mtgbox.collection.application;

import com.wcs.mtgbox.auth.domain.service.auth.JwtTokenService;
import com.wcs.mtgbox.collection.domain.dto.AddCollectionCardDto;
import com.wcs.mtgbox.collection.domain.dto.CollectionCardDto;
import com.wcs.mtgbox.collection.domain.service.CollectionCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Collection management", description = "API to handle user's card collections")
@RestController
@RequestMapping("/api/v1/collection-cards")
public class CollectionCardController {

    private final CollectionCardService collectionCardService;
    private final JwtTokenService jwtTokenService;


    public CollectionCardController(
            CollectionCardService collectionCardService,
            JwtTokenService jwtTokenService
    ) {
        this.collectionCardService = collectionCardService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Get all collection cards from user", description = "Retrieves all the cards in a user's collection using their ID")

    public ResponseEntity<List<CollectionCardDto>> readAllCollectionCardsFromUser(@PathVariable Long id) {
        List<CollectionCardDto> cards = collectionCardService.getCollectionCardsByUserId(id);
        return ResponseEntity.ok(cards);
    }

    @PostMapping
    @Operation(summary = "Add collection cards", description = "Allows a user to add new cards to their collection")

    public ResponseEntity<?> addCollectionCards(@RequestBody List<AddCollectionCardDto> addCardsBody, HttpServletRequest request) {
        return ResponseEntity.status(201).body(collectionCardService.saveCollectionCards(addCardsBody, getTokenUserId(request)));
    }

    @DeleteMapping("/usercard/{userCardId}")
    @Operation(summary = "Remove user card by User Card ID", description = "Allows a user to remove a card from their collection using its ID")

    public ResponseEntity<?> removeUserCardId(@PathVariable Long userCardId, HttpServletRequest request) {
        collectionCardService.removeUserCardId(userCardId, getTokenUserId(request));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/usercards")
    @Operation(summary = "Remove user cards by IDs", description = "Allows a user to remove multiple cards from their collection using their IDs")

    public ResponseEntity<?> removeUserCardsById(@RequestBody List<Long> cardsIdList, HttpServletRequest request) {
        collectionCardService.removeUserCardsByUserCardIds(cardsIdList, getTokenUserId(request));
        return ResponseEntity.ok().build();
    }

    private long getTokenUserId(HttpServletRequest request) {
        return jwtTokenService.getUserIdFromToken(request);
    }
}
