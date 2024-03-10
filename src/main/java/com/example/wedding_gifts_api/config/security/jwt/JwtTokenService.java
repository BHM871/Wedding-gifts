package com.example.wedding_gifts_api.config.security.jwt;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.wedding_gifts_api.adapters.security.TokenManagerAdapter;
import com.example.wedding_gifts_api.common.LimitTimeForToken;
import com.example.wedding_gifts_api.common.MyZone;
import com.example.wedding_gifts_api.core.domain.exceptions.account.AccountForbiddenException;
import com.example.wedding_gifts_api.core.domain.model.Account;
import com.example.wedding_gifts_api.core.usecases.token.ITokenUseCase;
import com.example.wedding_gifts_api.infra.dtos.token.SaveTokenDTO;

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

    @Override
    public void validateSessionId(String token, UUID pathVariableId) throws Exception {
        String subject = validateToken(token);
        String idToken = subject.split(",")[0];
        if(UUID.fromString(idToken).compareTo(pathVariableId) != 0) {
            throw new AccountForbiddenException("Account Id is not your");
        }
    }
}
