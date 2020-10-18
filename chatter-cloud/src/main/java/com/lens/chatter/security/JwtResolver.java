package com.lens.chatter.security;

import com.lens.chatter.enums.Role;
import com.lens.chatter.exception.UnauthorizedException;
import com.lens.chatter.model.dto.user.InviteMailDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import static com.lens.chatter.constant.ErrorConstants.EXPIRED_TOKEN;
import static com.lens.chatter.constant.ErrorConstants.INVALID_TOKEN;

@Component
public class JwtResolver {

    @Value("${jwt.secret}")
    private String secret;

    public UUID getIdFromToken(String token) {
        tokenExpireCheck(token);
        String idString;
        try {
            idString = getClaimFromToken(token, Claims::getSubject);
        } catch (Exception e) {
            throw new UnauthorizedException(INVALID_TOKEN);
        }
        return UUID.fromString(idString);
    }

    public Role getRoleFromToken(String token) {
        tokenExpireCheck(token);
        try {
            return Role.valueOf(getClaimFromToken(token, Claims::getAudience));
        } catch (Exception e) {
            throw new UnauthorizedException(INVALID_TOKEN);
        }
    }

    public String getMailFromToken(String token) {
        tokenExpireCheck(token);
        try {
            return getClaimFromToken(token, Claims::getIssuer);
        } catch (Exception e) {
            throw new UnauthorizedException(INVALID_TOKEN);
        }
    }

    public InviteMailDto getInviteTokensInfo(String token) {
        tokenExpireCheck(token);
        InviteMailDto inviteMailDto = new InviteMailDto();
        try {
            Claims claims = getAllClaimsFromToken(token);
            inviteMailDto.setMail(claims.getIssuer());
            inviteMailDto.setRole(Role.valueOf(claims.getAudience()));
            inviteMailDto.setTitle(claims.get("title").toString());
            return inviteMailDto;
        } catch (Exception e) {
            throw new UnauthorizedException(INVALID_TOKEN);
        }
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private void tokenExpireCheck(String token) {
        try {
            if (new Date().after(getClaimFromToken(token, Claims::getExpiration))) {
                throw new UnauthorizedException(EXPIRED_TOKEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException(INVALID_TOKEN);
        }
    }

}
