package com.lth.hotelmanagement.response;

import java.util.List;

public class JwtResponse {
    private Long id;
    private String email;
    private String jwt;
    private String type = "Bearer";
    private List<String> roles;

    public JwtResponse() {
    }

    public JwtResponse(Long id, String email, String jwt, List<String> roles) {
        this.id = id;
        this.email = email;
        this.jwt = jwt;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
