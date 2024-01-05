package com.wcs.mtgbox.auth.domain.entity;

import jakarta.persistence.*;

@Entity
@Table
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    public Role() {
    }

    public Role(String type) {
        this.type = type; // ADMIN, USER, ...
    }

    public Role(Long id ) {
        this.id = id; // ADMIN, USER, ...
    }

    public Role(Long id, String type ) {
        this.id = id;
        this.type = type;// ADMIN, USER, ...
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String role) {
        this.type = role;
    }
}
