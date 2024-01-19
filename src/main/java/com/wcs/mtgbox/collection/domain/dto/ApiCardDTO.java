package com.wcs.mtgbox.collection.domain.dto;

import io.magicthegathering.javasdk.resource.ForeignData;

public class ApiCardDTO {
    private String cardIdApi;
    private String name;
    private ForeignData[] foreignNames;
    private String imageUrl;
    private String manaCost;
    private String rarity;
    private String set;
    private String setName;
    private String artist;
    private String text;

    public ApiCardDTO(){
    }

    public ApiCardDTO(String cardIdApi, String name, ForeignData[] foreignNames, String imageUrl, String manaCost, String rarity, String set, String setName, String artist, String text) {
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

    public String getCardIdApi() {
        return cardIdApi;
    }

    public void setCardIdApi(String cardIdApi) {
        this.cardIdApi = cardIdApi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ForeignData[] getForeignNames() {
        return foreignNames;
    }

    public void setForeignNames(ForeignData[] foreignNames) {
        this.foreignNames = foreignNames;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
