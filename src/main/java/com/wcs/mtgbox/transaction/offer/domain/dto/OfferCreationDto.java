package com.wcs.mtgbox.transaction.offer.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OfferCreationDto {
    private Long wantedUserCardId;
    private Long userId;
    private List<Long> userCardIds;
}
