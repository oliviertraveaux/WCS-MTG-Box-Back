package com.wcs.mtgbox.collection.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCardOnMarketDto {
    Long userCardId;
    Long userId;
    String userName;
    String city;
    String quality;
    String language;
    Integer department;
    Boolean hasAnOffer;
}
