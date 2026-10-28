package com.villegaca.ironmetrics.service;

import java.time.Instant;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@Service 
@AllArgsConstructor 
public class JwtService {
    private final JwtEncoder jwtEncoder;

    //generate token
    public String generateToken(String email) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("ironMetrics")
            .subject(email)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(3600))
            .build();

        String token = jwtEncoder.encode(
            JwtEncoderParameters.from(claims)
        ).getTokenValue();

        return token;
    }
}
