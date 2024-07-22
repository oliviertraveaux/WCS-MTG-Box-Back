package com.wcs.mtgbox.collection.domain.dto;

import io.magicthegathering.javasdk.resource.ForeignData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiCardDTO {
    private String cardIdApi;
    private String name;
    private ForeignData[] foreignNames;
    private String imageUrl;
    private Integer manaCost;
    private String rarity;
    private String set;
    private String setName;
    private String artist;
    private String text;

    public ApiCardDTO(){
    }

    public ApiCardDTO(String cardIdApi, String name, ForeignData[] foreignNames, String imageUrl, Integer manaCost, String rarity, String set, String setName, String artist, String text) {
        this.cardIdApi = cardIdApi;
        this.name = name;
        this.foreignNames = foreignNames;
        this.imageUrl = imageUrl;
        this.manaCost = manaCost;
        this.rarity = rarity;
        this.set = set;
        this.setName = setName;
        this.artist = artist;
        this.text = text;
    }

}
