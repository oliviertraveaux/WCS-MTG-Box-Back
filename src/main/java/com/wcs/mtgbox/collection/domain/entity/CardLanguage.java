package com.wcs.mtgbox.collection.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class CardLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_language_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "cardLanguage", cascade = CascadeType.REMOVE)
    private List<UserCard> userCards;

}
