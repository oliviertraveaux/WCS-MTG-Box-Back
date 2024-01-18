package com.wcs.mtgbox.auth.domain.dto;

public class UserRegistrationDTO {
    private String username;

    private String email;

    private String password;

    private int postCode;

    private String city;

    public UserRegistrationDTO(String username, String email, String password, int postCode, String city) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.postCode = postCode;
        this.city = city;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
