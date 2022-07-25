package uz.pdp.news_app.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.news_app.entity.Role;

import java.util.Date;

@Component
public class JwtProvider {

    long expireTime = 36000 * 1000;
    String secretKey = "ketmon"; // secret key

    public String generateToken(String username, Role role) {
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime)) // now ex: 2022 07 25 + 20 day >>> expireTime
                .claim("role", role.getName())
                .signWith(SignatureAlgorithm.HS512, secretKey) // Encode
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
