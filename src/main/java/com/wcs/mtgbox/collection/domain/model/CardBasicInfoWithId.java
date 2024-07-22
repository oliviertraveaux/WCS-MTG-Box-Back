package com.wcs.mtgbox.collection.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardBasicInfoWithId extends CardBasicInfo {
    private Long uniqueId;
}
