package com.esempio.Ecommerce.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.esempio.Ecommerce.model.Entity.LocalUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;
    private Algorithm algorithm;
    private static final String USERNAME_KEY = "USERNAME";
    private static final String REALM_ROLES_KEY = "realm_access.roles";
    private static final String RESOURCE_ROLES_KEY = "resource_access.vercarix-rest-api.roles";

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateJWT(LocalUser user) {
        return JWT.create()
                .withClaim(USERNAME_KEY, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (expiryInSeconds * 1000L)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getUsername(String token) {
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }

    public List<String> getRealmRoles(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim(REALM_ROLES_KEY).asList(String.class);
    }

    public List<String> getResourceRoles(String token, String resource) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("resource_access." + resource + ".roles").asList(String.class);
    }
}
