package com.wcs.mtgbox.transaction.offer.domain.service;

import com.wcs.mtgbox.transaction.offer.domain.dto.OfferCreationDto;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferDto;
import com.wcs.mtgbox.transaction.offer.domain.dto.OfferFullWantedCardDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OfferService {
    OfferCreationDto saveOffer(OfferCreationDto offer, HttpServletRequest request);
    OfferDto getOfferById(Long offerId);
    List<OfferDto> getOffersByUserCardId(Long userCardId);
    List<OfferFullWantedCardDto> getOffersMadeByUserId(Long userId);
    List<OfferFullWantedCardDto> getOffersReceivedByUserId(Long userId);
    List<OfferFullWantedCardDto> getOffersHistoryByUserId(Long userId);
    void deleteOffer(Long offerId, HttpServletRequest request);
    OfferDto acceptOffer(Long offerId, String offerStatus, HttpServletRequest request);
    OfferDto validateOffer(Long offerId, String offerStatus, HttpServletRequest request);
    OfferDto cancelOffer(Long offerId, String offerStatus, HttpServletRequest request);

}
