package com.seplag.acervo.security.controller;

import com.seplag.acervo.security.dto.LoginRequest;
import com.seplag.acervo.security.dto.RefreshRequest;
import com.seplag.acervo.security.dto.TokenResponse;
import com.seplag.acervo.security.service.TokenService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String accessToken = tokenService.generateAccessToken(auth);
        String refreshToken = tokenService.generateRefreshToken(auth);

        TokenResponse resp = new TokenResponse();
        resp.setAccessToken(accessToken);
        resp.setRefreshToken(refreshToken);
        resp.setExpiresInSeconds(tokenService.getAccessExpirationSeconds());

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest request) {
        String newAccessToken = tokenService.refreshAccessToken(request.getRefreshToken());

        TokenResponse resp = new TokenResponse();
        resp.setAccessToken(newAccessToken);
        resp.setExpiresInSeconds(tokenService.getAccessExpirationSeconds());

        return ResponseEntity.ok(resp);
    }
}