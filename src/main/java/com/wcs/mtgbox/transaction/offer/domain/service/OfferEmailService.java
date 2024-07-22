package com.wcs.mtgbox.transaction.offer.domain.service;


import com.mailjet.client.errors.MailjetException;
import com.wcs.mtgbox.transaction.offer.domain.entity.Offer;

public interface OfferEmailService {
    public void sendOfferEmail(Offer offer) throws MailjetException;
}
