package it.beachill.model.services.implementation;

import it.beachill.model.repositories.abstractions.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LogoutService implements LogoutHandler {

    TokenRepository tokenRepository;

    @Autowired
    public LogoutService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            try {
                response.sendError(403);
            } catch (IOException e) {
                try {
                    response.sendError(500);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByTokenAndRevokedFalseAndExpiredFalse(jwt).orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        } else {
            try {
                response.sendError(403);
            } catch (IOException e) {
                try {
                    response.sendError(500);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
