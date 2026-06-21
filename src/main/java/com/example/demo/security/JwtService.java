package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private String createToken(UserDetails userDetails)
    {
        String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();

        return Jwts.builder().
                signWith(getSignInKey(SECRET_KEY))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .claim("role",role)
                .compact();
    }
    public String generateToken(UserDetails userDetails)
    {
        return createToken(userDetails);
    }

    private Key getSignInKey(String secretKey){
        byte[] bytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String getUsername(String token)
    {
        return extractClaim(token,Claims::getSubject);
    }

    public boolean isValid(String token , UserDetails userDetails)
    {
        return userDetails.getUsername().equals(getUsername(token)) && !extractExpiryDate(token).before(new Date());
    }
    private Date extractExpiryDate(String token)
    {
        return extractClaim(token,Claims::getExpiration);
    }

    private <T> T extractClaim(String token , Function<Claims,T> claimResolver)
    {
        return claimResolver.apply(extractClaims(token));
    }
    private Claims extractClaims(String token)
    {
        return Jwts.parserBuilder().
                setSigningKey(getSignInKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
