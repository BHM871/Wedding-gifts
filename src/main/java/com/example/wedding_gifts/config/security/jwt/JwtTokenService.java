package com.example.wedding_gifts.config.security.jwt;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.wedding_gifts.adapters.security.TokenManagerAdapter;
import com.example.wedding_gifts.common.LimitTimeForToken;
import com.example.wedding_gifts.common.MyZone;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.token.ITokenUseCase;
import com.example.wedding_gifts.infra.dtos.token.SaveTokenDTO;

@Service
public class JwtTokenService implements TokenManagerAdapter {

    @Value("${api.security.token.secret}")
    private String secret;
    private String issuer = "wedding_gifts_api";

    @Autowired
    private ITokenUseCase tokenService;

    @Override
    public String generatorToken(Account account) throws Exception {
        try {
            String actualToken = tokenService.getTokenByAccount(account.getId());
            if(actualToken != null) return actualToken;

            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer(issuer)
                .withSubject(account.getId()+","+account.getEmail())
                .withExpiresAt(LimitTimeForToken.genExpirationInstant())
                .sign(algorithm);

            tokenService.saveToken(new SaveTokenDTO(token, LocalDateTime.ofInstant(LimitTimeForToken.genExpirationInstant(), MyZone.zoneId()), account.getId()));

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
