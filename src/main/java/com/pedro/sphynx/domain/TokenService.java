package com.pedro.sphynx.domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pedro.sphynx.infrastructure.entities.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    public String generateToken(User user){
        try{
            var algorithm = Algorithm.HMAC256("12345678");
            return JWT.create()
                    .withIssuer("API Sphynx")
                    .withSubject(user.getUser())
                    .withExpiresAt(dtExpires())
                    .sign(algorithm);

        } catch(JWTCreationException exception){
            throw new RuntimeException("ERROR IN TOKEN GENERATE", exception);
        }
    }

    public boolean isValid(String tokenJWT){
        try{
            var algorithm = Algorithm.HMAC256("12345678");
            var valid = JWT.require(algorithm)
                    .withIssuer("API Sphynx")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
            return true;
        } catch(JWTVerificationException exception){
            return false;
        }
    }

    private Instant dtExpires(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
