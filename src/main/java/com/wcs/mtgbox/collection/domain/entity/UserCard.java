package com.wcs.mtgbox.collection.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wcs.mtgbox.auth.domain.entity.User;
import jakarta.persistence.*;

@Entity
public class UserCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_card_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "user_id",  nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "quality_id")
    private CardQuality cardQuality;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "language_id")
    private CardLanguage cardLanguage;

    @Column(name = "is_active")
    private String isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public CardQuality getCardQuality() {
        return cardQuality;
    }

    public void setCardQuality(CardQuality cardQuality) {
        this.cardQuality = cardQuality;
    }

    public CardLanguage getCardLanguage() {
        return cardLanguage;
    }

    public void setCardLanguage(CardLanguage cardLanguage) {
        this.cardLanguage = cardLanguage;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
