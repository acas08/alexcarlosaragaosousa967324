package com.seplag.acervo.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {

    private String tokenType = "Bearer";
    private String accessToken;
    private String refreshToken;
    private long expiresInSeconds;

}
