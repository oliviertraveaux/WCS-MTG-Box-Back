package com.wcs.mtgbox.transaction.offer.domain.dto;

import lombok.Getter;

@Getter
public enum OfferStatusEnum {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    VALIDATED("VALIDATED");

    private final String fullName;

    OfferStatusEnum(String fullName) {
        this.fullName = fullName;
    }

}
