package com.rogerli.springmall.service;

import com.rogerli.springmall.dto.UserLoginRequest;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Calendar;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JWTService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("${jwt.secret}")
    private String KEY;
    public String generateToken(UserLoginRequest request) {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        
        Claims claims = Jwts.claims()
                .subject(userDetails.getUsername())  // Add username to token
                .expiration(calendar.getTime())
                .issuer("JWT Test")
                .add("roles", userDetails.getAuthorities())
                .build();
                
        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());
        return Jwts.builder().claims(claims)
                .signWith(secretKey)
                .compact();
    }

    public Map<String, Object> parseToken(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(KEY.getBytes());
        JwtParser parser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
        Jws<Claims> claims = parser.parseSignedClaims(token);
        return claims.getPayload().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}