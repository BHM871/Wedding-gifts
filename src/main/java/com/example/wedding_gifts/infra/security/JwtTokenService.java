package com.example.wedding_gifts.infra.security;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.wedding_gifts.adapters.security.ITokenManager;
import com.example.wedding_gifts.commun.LimitTimeForToken;
import com.example.wedding_gifts.core.domain.dtos.token.SaveTokenDTO;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.token.ITokenUseCase;

@Service
public class JwtTokenService implements ITokenManager {

    @Value("${api.security.token.secret}")
    private String secret;
    private String issuer = "wedding_gifts_api";

    @Autowired
    private ITokenUseCase tokenService;

    @Override
    public String generatorToken(Account account) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer(issuer)
                .withSubject(account.getEmail())
                .withExpiresAt(LimitTimeForToken.genExpirationInstant())
                .sign(algorithm);

            tokenService.saveToken(new SaveTokenDTO(token, LocalDateTime.ofInstant(LimitTimeForToken.genExpirationInstant(), ZoneId.of("-03:00")), account.getId()));

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }
    
}
