package com.wcs.mtgbox.collection.application;

import com.wcs.mtgbox.collection.domain.dto.AddCollectionCardDto;
import com.wcs.mtgbox.collection.domain.dto.CollectionCardDto;
import com.wcs.mtgbox.collection.domain.service.CollectionCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<CollectionCardDto>> readAllCollectionCardsFromUser(@PathVariable Long id) {
        List<CollectionCardDto> cards = collectionCardService.getCollectionCardsByUserId(id);
        return ResponseEntity.ok(cards);
    }

    @PostMapping
    public ResponseEntity<?> addCollectionCards(@RequestBody List<AddCollectionCardDto> addCardsBody) {
        return ResponseEntity.status(201).body(collectionCardService.saveCollectionCards(addCardsBody));
    }

    @DeleteMapping("/usercard/{userCardId}")
    public ResponseEntity<?> removeUserCardId(@PathVariable Long userCardId) {
        collectionCardService.removeUserCardId(userCardId);
        return ResponseEntity.ok().build();
    }
}
