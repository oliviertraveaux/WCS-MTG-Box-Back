package com.wcs.mtgbox.auth.domain.dto;

import com.wcs.mtgbox.auth.domain.entity.Role;
import com.wcs.mtgbox.files.domain.entity.Media;

import java.time.LocalDateTime;

public class UserDTO {
    private Long id;

    private String username;

    private String email;


    private Boolean isActive;

    private Boolean isBanned;

    private int postCode;

    private String city;

    private LocalDateTime lastConnectionDate;

    private LocalDateTime creationDate;

    private Role role;

    private Media avatar;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String email, Boolean isActive, Boolean isBanned, int postCode, String city, LocalDateTime lastConnectionDate, LocalDateTime creationDate, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isActive = isActive;
        this.isBanned = isBanned;
        this.postCode = postCode;
        this.city = city;
        this.lastConnectionDate = lastConnectionDate;
        this.creationDate = creationDate;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDateTime getLastConnectionDate() {
        return lastConnectionDate;
    }

    public void setLastConnectionDate(LocalDateTime lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Media getAvatar() {
        return avatar;
    }

    public void setAvatar(Media avatar) {
        this.avatar = avatar;
    }
}