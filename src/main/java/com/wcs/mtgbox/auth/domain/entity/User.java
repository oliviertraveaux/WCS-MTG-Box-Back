package com.wcs.mtgbox.auth.domain.entity;

import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.files.domain.entity.Media;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`user`")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_banned", nullable = false)
    private Boolean isBanned = false;

    @Column(name = "department", nullable = false)
    private int department;

    @Column(name = "city", nullable = false)
    private String city;

    @LastModifiedDate
    @Column(name = "last_connection_date", nullable = false)
    private LocalDateTime lastConnectionDate;

    @CreatedDate
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserCard> userCards;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id")
    private Media media;

}
