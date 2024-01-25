package it.beachill.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponseDto {

    @JsonProperty("access_token")
    private String token;

    public AuthenticationResponseDto() {
    }

    public AuthenticationResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
