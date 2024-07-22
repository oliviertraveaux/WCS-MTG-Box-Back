package com.wcs.mtgbox.collection.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OfferWantedCardUserInfo {
    private Long UserCardId;
    private Long userId;
    private String userName;
    private String city;
    private Integer department;
    private String qualityName;
    private String languageName;
}
