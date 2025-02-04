

package com.thangavel.authentication.config;

import com.thangavel.authentication.dao.UserDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    private final Key signingKey;

    public JwtUtils(@Value("${security.jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDao userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDao userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDao user, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail())  // Store UUID as Subject
                .claim("userId", user.getId().toString())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDao userDetails) {
        final String userId = extractClaim(token, Claims::getSubject);
        return (userId.equals(userDetails.getId().toString())) && !isTokenExpired(token);
    }

    public Map<String, Object> validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Convert Claims to Map<String, Object>
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("userId", claims.get("userId", String.class)); // UUID as Subject
            userDetails.put("email", claims.get("email", String.class));
            userDetails.put("firstName", claims.get("firstName", String.class));
            userDetails.put("lastName", claims.get("lastName", String.class));

            return userDetails;

        } catch (Exception e) {
            return null;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
