package com.julian.authservice.dto;

public class UserDTO {
    private Long id;
    private String username;
    private String email;

    public UserDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getters (no necesitas setters si no vas a modificar)
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
