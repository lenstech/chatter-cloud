package com.lens.chatter.security;

import com.lens.chatter.constant.Role;
import com.lens.chatter.exception.BadExceptionRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import static com.lens.chatter.constant.ErrorConstants.INVALID_TOKEN;

@Component
public class JwtResolver {

    @Value("${jwt.secret}") private String secret;

    // Extracts the username(id) from the given token
    public UUID getIdFromToken(String token) {
        String idString;
        try{
         idString = getClaimFromToken(token, Claims::getSubject);
        } catch (Exception e){
            throw new BadExceptionRequest(INVALID_TOKEN);
        }
        return UUID.fromString(idString);
    }

    // Extracts the role from the given token
    public Role getRoleFromToken(String token) {
        try{
         return Role.valueOf(getClaimFromToken(token, Claims::getAudience));
        } catch (Exception e){
            throw new BadExceptionRequest(INVALID_TOKEN);
        }
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


}
