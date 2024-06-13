package com.wcs.mtgbox.collection.application;

import com.wcs.mtgbox.collection.domain.dto.AddCollectionCardDto;
import com.wcs.mtgbox.collection.domain.dto.CollectionCardDto;
import com.wcs.mtgbox.collection.domain.service.CollectionCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Collection management", description = "API to handle user's card collections")
@RestController
@RequestMapping("/api/v1/collection-cards")
public class CollectionCardController {

    private final CollectionCardService collectionCardService;

    public CollectionCardController(
            CollectionCardService collectionCardService
    ) {
        this.collectionCardService = collectionCardService;
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Get all collection cards from user", description = "Retrieves all the cards in a user's collection using their ID")

    public ResponseEntity<List<CollectionCardDto>> readAllCollectionCardsFromUser(@PathVariable Long id) {
        List<CollectionCardDto> cards = collectionCardService.getCollectionCardsByUserId(id);
        return ResponseEntity.ok(cards);
    }

    @PostMapping
    @Operation(summary = "Add collection cards", description = "Allows a user to add new cards to their collection")

    public ResponseEntity<?> addCollectionCards(@RequestBody List<AddCollectionCardDto> addCardsBody) {
        return ResponseEntity.status(201).body(collectionCardService.saveCollectionCards(addCardsBody));
    }

    @DeleteMapping("/usercard/{userCardId}")
    @Operation(summary = "Remove user card by User Card ID", description = "Allows a user to remove a card from their collection using its ID")

    public ResponseEntity<?> removeUserCardId(@PathVariable Long userCardId) {
        collectionCardService.removeUserCardId(userCardId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/usercards")
    @Operation(summary = "Remove user cards by IDs", description = "Allows a user to remove multiple cards from their collection using their IDs")

    public ResponseEntity<?> removeUserCardsById(@RequestBody List<Long> cardsIdList) {
        collectionCardService.removeUserCardsByUserCardIds(cardsIdList);
        return ResponseEntity.ok().build();
    }
}
