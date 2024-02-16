package com.wcs.mtgbox.collection.domain.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class CardLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_language_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "cardLanguage", cascade = CascadeType.REMOVE)
    private List<UserCard> userCards;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String text) {
        this.name = text;
    }

    public List<UserCard> getUserCards() {
        return userCards;
    }

    public void setUserCards(List<UserCard> userCards) {
        this.userCards = userCards;
    }
}
