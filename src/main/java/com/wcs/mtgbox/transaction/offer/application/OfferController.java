package com.wcs.mtgbox.transaction.offer.application;


import com.wcs.mtgbox.collection.infrastructure.exception.UserCardNotFoundErrorException;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferCreationDto;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferStatusDto;
import com.wcs.mtgbox.transaction.offer.domain.service.OfferService;
import com.wcs.mtgbox.transaction.offer.infrastructure.exception.OfferNotFoundErrorException;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Offer management", description = "API to handle user offers for cards")
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
    @Operation(summary = "Create new offer", description = "Allows a user to create a new offer for a card")
    ResponseEntity<?> readOneOffer(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(offerService.getOfferById(id));
        } catch (Exception e){
            throw new OfferNotFoundErrorException(id);
        }
    }

    @GetMapping("/card-ad/{id}")
    @Operation(summary = "Get offer by ID", description = "Retrieves the details of an offer using its ID")
    ResponseEntity<?> readOffersByUserCardId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(offerService.getOffersByUserCardId(id));
        } catch (Exception e){
            throw new UserCardNotFoundErrorException(id);
        }
    }
    @GetMapping("/user/{id}")
    @Operation(summary = "Get offers by user  ID", description = "Retrieves all the offers made for a specific user  using its ID")
    ResponseEntity<?> readOffersByUserId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(offerService.getOffersMadeByUserId(id));
        } catch (Exception e){
            throw new UserCardNotFoundErrorException(id);
        }
    }

    @GetMapping("/received/user/{id}")
    @Operation(summary = "Get offers  received by user ID", description = "Retrieves all the offers received  for a specific user using its ID")
    ResponseEntity<?> readOffersReceivedByUserId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(offerService.getOffersReceivedByUserId(id));
        } catch (Exception e){
            throw new UserCardNotFoundErrorException(id);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an offer by ID", description = "Delete an offer using its ID")
    public ResponseEntity<?> removeOfferById(@PathVariable Long id, HttpServletRequest request) {
        offerService.deleteOffer(id, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/accept-offer/{id}")
    @Operation(summary = "Modify offer received by ID", description = "Modify the offer status to ACCEPTED by using its ID")
    public ResponseEntity<?> acceptOffer(@PathVariable Long id, @RequestBody OfferStatusDto status, HttpServletRequest request)  {
        return ResponseEntity.ok(offerService.acceptOffer(id, status.getStatus(), request));
    }

    @PutMapping("/validate-offer/{id}")
    @Operation(summary = "Modify offer received by ID", description = "Modify the offer status to VALIDATED by using its ID")
    public ResponseEntity<?> validateOffer(@PathVariable Long id, @RequestBody OfferStatusDto status, HttpServletRequest request) {
            return ResponseEntity.ok(offerService.validateOffer(id, status.getStatus(), request));
    }

    @PutMapping("/cancel-offer/{id}")
    @Operation(summary = "Modify offer received by ID", description = "Modify the offer status to PENDING by using its ID")
    public ResponseEntity<?> cancelOffer(@PathVariable Long id, @RequestBody OfferStatusDto status, HttpServletRequest request) {
            return ResponseEntity.ok(offerService.cancelOffer(id, status.getStatus(), request));
    }
}
