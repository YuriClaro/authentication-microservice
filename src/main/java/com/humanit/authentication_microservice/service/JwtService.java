package com.humanit.authentication_microservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final UserDetailsService userDetailsService;
    private static final String SECRET =
            "t2JPoQSdHCCT7jqfTBzzVgasd22SsgXIcW67UEqjsalnjTnBpdt57TBGZOi87LtKbhOxy4mDP7MEsp5EMIQLZjyOQfsdgh";
    private static final long EXPIRATION_TIME = 864_000_000;

    public JwtService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return authorizationHeader;
    }

    public boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validateTokenResponse(String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        UserDetails userDetails = userDetailsService.loadUserByUsername(extractUsername(token));
        return validateToken(token, userDetails);
    }


}
