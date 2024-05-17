package com.wcs.mtgbox.transaction.offer.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "`offer`")
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "wanted_user_card_id")
    private UserCard wantedUserCard;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "status",  nullable = false)
    private String status;

    @Column(name = "created_date",  nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "accepted_date")
    private LocalDateTime acceptedDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "offer_user_card",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_card_id")
    )
    private List<UserCard> userCards;
}
