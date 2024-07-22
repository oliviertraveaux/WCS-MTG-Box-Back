package com.wcs.mtgbox.collection.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter @Setter
@NoArgsConstructor
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
    private String setAbbreviation;

    @Column(name = "set_name")
    private String setName;

    @Column(name = "text", columnDefinition = "longtext")
    private String text;

    @Column(name = "artist")
    private String artist;

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<UserCard> userCards;

}
