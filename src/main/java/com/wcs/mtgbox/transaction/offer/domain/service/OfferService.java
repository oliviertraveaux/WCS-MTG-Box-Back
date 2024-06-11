package com.wcs.mtgbox.transaction.offer.domain.service;

import com.wcs.mtgbox.transaction.offer.domain.dto.OfferCreationDto;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferDto;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferFullWantedCardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OfferService {
    OfferCreationDto saveOffer(OfferCreationDto offer);
    OfferDto getOfferById(Long offerId);
    List<OfferDto> getOffersByUserCardId(Long userCardId);
    List<OfferFullWantedCardDto> getOffersByUserId(Long userId);
    List<OfferFullWantedCardDto> getOffersReceivedByUserId(Long userId);
}
