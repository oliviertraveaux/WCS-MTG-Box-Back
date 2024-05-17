package com.wcs.mtgbox.transaction.offer.domain.service;

import com.wcs.mtgbox.transaction.offer.domain.dto.OfferDto;
import org.springframework.stereotype.Service;

@Service
public interface OfferService {
    OfferDto saveOffer(OfferDto offer);
}
