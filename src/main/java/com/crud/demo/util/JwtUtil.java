package com.crud.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import com.crud.demo.model.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // HS256 algorithm

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

        // Mendapatkan role tunggal dari token

      public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);  // "role" adalah klaim yang menyimpan single role
    }
    
    //   // Extract username from token
    // public String extractUsername(String token) {
    //     return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    // }

//     // Extract role from token
//    public String extractRole(String token) {
//     Claims claims = Jwts.parserBuilder()
//             .setSigningKey(secretKey)
//             .build() // Create a JwtParser
//             .parseClaimsJws(token)
//             .getBody();
//     return claims.get("role", String.class); // Get the role from claims
// }
    


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(secretKey)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

 

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String  username,String role) {
        Map<String, Object> claims = new HashMap<>();
                // return Jwts.builder()
                // .setSubject(user.getUsername())
                // .claim("role", user.getRole()) // Include the role in the token
                // .setIssuedAt(new Date(System.currentTimeMillis()))
                // .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                // .signWith(secretKey)
                // .compact();

        return createToken(claims, username,role);
    }

    private String createToken(Map<String, Object> claims, String subject,String role) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(subject)
                   .claim("role", role)  // Simpan klaim single role sebagai string
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                   .signWith(secretKey)
                   .compact();
    }

    

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
