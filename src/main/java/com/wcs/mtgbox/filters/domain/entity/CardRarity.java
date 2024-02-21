package com.wcs.mtgbox.filters.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class CardRarity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_rarity_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
