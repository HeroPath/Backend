package com.gianca1994.aowebbackend.resources.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @Author: Gianca1994
 * Explanation: JwtTokenUtil
 */

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${jwt.expiration.time.token.seconds}")
    public Long EXPIRATION_TIME_TOKEN;

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public String getUsernameFromToken(String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get the username from the token.
         * @param String token
         * @return String
         */
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get the expiration date from the token.
         * @param String token
         * @return Date
         */
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get the claim from the token.
         * @param String token
         * @param Function<Claims, T> claimsResolver
         * @return User user
         */
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get the all claims from the token.
         * @param String token
         * @return Claims
         */
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to check if the token is expired.
         * @param String token
         * @return Boolean
         */
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to generate the token.
         * @param UserDetails userDetails
         * @return String
         */
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to generate the token.
         * @param Map<String, Object> claims
         * @param String subject
         * @return String
         */

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_TOKEN * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to validate the token.
         * @param String token
         * @param UserDetails userDetails
         * @return Boolean
         */
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
