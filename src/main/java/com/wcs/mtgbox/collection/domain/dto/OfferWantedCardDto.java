package com.wcs.mtgbox.collection.domain.dto;

import com.wcs.mtgbox.collection.domain.model.CardBasicInfoWithId;
import com.wcs.mtgbox.collection.domain.model.OfferWantedCardUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferWantedCardDto {
    private CardBasicInfoWithId cardInfo;
    private OfferWantedCardUserInfo userInfo;
}
