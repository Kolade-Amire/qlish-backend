package com.qlish.qlish_api.auth;

import com.qlish.qlish_api.util.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //uses generics to extract any type of claims possible from token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(System.getenv("JWT_SECRET_KEY"));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //calls the generics method to extract specific claim(username)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String buildToken(Map<String, Object> claims, UserDetails userDetails, long expiration) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuer(SecurityConstants.JWT_ISSUER)
                .setAudience(SecurityConstants.AUDIENCE)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token)
                .before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public String generateAccessToken(Map<String, Object> claims, UserDetails userDetails) {
        long accessTokenExpiration = SecurityConstants.ACCESS_TOKEN_EXPIRATION;
        return buildToken(claims, userDetails, accessTokenExpiration);
    }

    public String generateAccessToken(UserDetails userDetails) {
        var claims = extractClaimsFromUser(userDetails);
        return generateAccessToken(claims, userDetails);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        long refreshTokenExpiration = SecurityConstants.REFRESH_TOKEN_EXPIRATION;
        var userClaims = extractClaimsFromUser(userDetails);
        return buildToken(userClaims, userDetails, refreshTokenExpiration);
    }


    public Map<String, Object> extractClaimsFromUser(UserDetails userDetails){
        var claims = new HashMap<String, Object>();
        var authorities = userDetails.getAuthorities().stream().toList();
        claims.put("authorities", authorities);
        claims.put("email", userDetails.getUsername());
        return claims;

    }

}