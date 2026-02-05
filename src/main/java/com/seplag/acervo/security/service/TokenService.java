package com.seplag.acervo.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;
    private final long accessExpirationSeconds;
    private final long refreshExpirationSeconds;

    public TokenService(
            JwtEncoder encoder,
            JwtDecoder decoder,
            @Value("${jwt.expiration-seconds}") long accessExpirationSeconds,
            @Value("${jwt.refresh-expiration-seconds}") long refreshExpirationSeconds
    ) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.accessExpirationSeconds = accessExpirationSeconds;
        this.refreshExpirationSeconds = refreshExpirationSeconds;
    }

    public String generateAccessToken(Authentication authentication) {
        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("acervo")
                .issuedAt(now)
                .expiresAt(now.plus(accessExpirationSeconds, ChronoUnit.SECONDS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("typ", "access")
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public String generateRefreshToken(Authentication authentication) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("acervo")
                .issuedAt(now)
                .expiresAt(now.plus(refreshExpirationSeconds, ChronoUnit.SECONDS))
                .subject(authentication.getName())
                .claim("typ", "refresh")
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public String refreshAccessToken(String refreshToken) {
        Jwt jwt = decoder.decode(refreshToken);

        Object typ = jwt.getClaims().get("typ");
        if (!"refresh".equals(typ)) {
            throw new IllegalArgumentException("Token informado não é refresh token.");
        }

        Instant now = Instant.now();

        JwtClaimsSet newClaims = JwtClaimsSet.builder()
                .issuer("acervo")
                .issuedAt(now)
                .expiresAt(now.plus(accessExpirationSeconds, ChronoUnit.SECONDS))
                .subject(jwt.getSubject())
                .claim("typ", "access")
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return encoder.encode(JwtEncoderParameters.from(header, newClaims)).getTokenValue();
    }

    public long getAccessExpirationSeconds() {
        return accessExpirationSeconds;
    }
}
