package com.wcs.mtgbox.collection.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserCardOnMarketSearchResultDto {
    private Long cardId;
    private String name;
    private String imageUrl;
    private String frenchImageUrl;
    private String setAbbreviation;
    private String setName;
    private String rarity;
    private String artist;
    private String text;
    private List<UserCardOnMarketDto> userCardsOnMarket;
}
