package it.beachill.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.beachill.model.entities.User;
import org.springframework.beans.factory.annotation.Value;

public class AuthenticationResponseDto {

    //@JsonProperty("access_token")
    private String token;
    private UserDto user;
    private long expirationDate;

    public AuthenticationResponseDto() {
    }

    public AuthenticationResponseDto(String token, User user) {
        this.token = token;
        if(user == null) {
            this.user = null;
        } else {
            this.user = new UserDto(user);
        }
        this.expirationDate = 86400000L;
    }

    public AuthenticationResponseDto(String token) {
        this.token = token;
        this.expirationDate = 86400000L;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }
}
