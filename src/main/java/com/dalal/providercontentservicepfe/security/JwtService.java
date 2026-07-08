package com.dalal.providercontentservicepfe.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${public-key}")
    private String publicKeyString;

    public PublicKey getPublicKeyFromString() throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(publicKeyString.trim());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
    public String extractUsername(String token) throws Exception {
        return extractClaim(token, Claims::getSubject);
    }

    public List<String> extractRoles(String token) throws Exception {
        return extractClaim(token,claims -> claims.get("roles", List.class));
    }

    public <R> R extractClaim(String token, Function<Claims, R> claimsResolver) throws Exception {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws Exception {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKeyFromString())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}

// none of them don't do any role at that case because
    /*
    * Jwts.parserBuilder()
                .setSigningKey(getPublicKeyFromString())
                .build()
                .parseClaimsJws(token)
                .getBody();
        check the validation of our token
    * */

//    public boolean isTokenValid(String token, String userEmail) throws Exception {
//        final String username = extractUsername(token);
//        return (username.equals(userEmail) && !isTokenExpired(token));
//    }
//
//    private boolean isTokenExpired(String token) throws Exception {
//        return extractClaim(token, Claims::getExpiration).before(new Date());
//    }
