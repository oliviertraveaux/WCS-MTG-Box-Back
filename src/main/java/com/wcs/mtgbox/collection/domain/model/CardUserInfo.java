package com.wcs.mtgbox.collection.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardUserInfo {
    private Long UserCardId;
    private Long userId;
    private String qualityName;
    private String languageName;
}
