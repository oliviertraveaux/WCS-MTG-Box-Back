package com.wcs.mtgbox.transaction.offer.application;


import com.wcs.mtgbox.collection.infrastructure.exception.UserCardNotFoundErrorException;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferCreationDto;
import com.wcs.mtgbox.transaction.offer.domain.service.OfferService;
import com.wcs.mtgbox.transaction.offer.infrastructure.exception.OfferNotFoundErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/offer")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<?> addCollectionCards(@RequestBody OfferCreationDto offerBody) {
        return ResponseEntity.status(201).body(offerService.saveOffer(offerBody));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> readOneOffer(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(offerService.getOfferById(id));
        } catch (Exception e){
            throw new OfferNotFoundErrorException(id);
        }
    }

    @GetMapping("/card-ad/{id}")
    ResponseEntity<?> readOffersByUserCardId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(offerService.getOffersByUserCardId(id));
        } catch (Exception e){
            throw new UserCardNotFoundErrorException(id);
        }
    }
}
