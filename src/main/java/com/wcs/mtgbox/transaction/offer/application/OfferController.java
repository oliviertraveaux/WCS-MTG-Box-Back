package com.wcs.mtgbox.transaction.offer.application;


import com.wcs.mtgbox.transaction.offer.domain.dto.OfferDto;
import com.wcs.mtgbox.transaction.offer.domain.service.OfferService;
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
    public ResponseEntity<?> addCollectionCards(@RequestBody OfferDto offerBody) {
        return ResponseEntity.status(201).body(offerService.saveOffer(offerBody));
    }
}
