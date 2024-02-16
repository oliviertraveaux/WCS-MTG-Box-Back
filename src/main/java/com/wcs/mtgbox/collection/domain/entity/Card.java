package com.wcs.mtgbox.collection.domain.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @Column(name = "api_card_id", nullable = false, unique = true)
    private String apiCardId;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "french_name")
    private String frenchName;

    @Column(name = "french_image_url")
    private String frenchImageUrl;

    @Column(name = "mana_cost", nullable = false)
    private Integer manaCost;

    @Column(name = "rarity")
    private String rarity;

    @Column(name = "set_abbreviation")
    private String set;

    @Column(name = "set_name")
    private String setName;

    @Column(name = "text")
    private String text;

    @Column(name = "artist")
    private String artist;

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<UserCard> userCards;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiCardId() {
        return apiCardId;
    }

    public void setApiCardId(String apiCardId) {
        this.apiCardId = apiCardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFrenchName() {
        return frenchName;
    }

    public void setFrenchName(String frenchName) {
        this.frenchName = frenchName;
    }

    public String getFrenchImageUrl() {
        return frenchImageUrl;
    }

    public void setFrenchImageUrl(String frenchImageUrl) {
        this.frenchImageUrl = frenchImageUrl;
    }

    public Integer getManaCost() {
        return manaCost;
    }

    public void setManaCost(Integer manaCost) {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public List<UserCard> getUserCards() {
        return userCards;
    }

    public void setUserCards(List<UserCard> userCards) {
        this.userCards = userCards;
    }
}
