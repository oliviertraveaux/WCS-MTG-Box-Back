package com.wcs.mtgbox.collection.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CardBasicInfo {
    private String apiCardId;
    private String name;
    private String frenchName = "";
    private String imageUrl;
    private String frenchImageUrl = "";
    private Integer manaCost;
    private String rarity;
    private String setAbbreviation;
    private String setName;
    private String artist;
    private String text;
}
