package com.lens.chatter.configuration;

import com.lens.chatter.enums.Role;
import com.lens.chatter.exception.UnauthorizedException;
import com.lens.chatter.security.JwtResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.NOT_AUTHORIZED_FOR_OPERATION;

/**
 * Created by Emir Gökdemir
 * on 9 Mar 2020
 */
@Configuration
public class AuthorizationConfig {

    @Autowired
    private JwtResolver jwtResolver;

    public UUID permissionCheck(String token, Role minAuthRole) {
        Role userRole = Role.NOT_AUTH;
        if (token != null) {
            userRole = jwtResolver.getRoleFromToken(token);
            greaterCheck(userRole, minAuthRole);
            return jwtResolver.getIdFromToken(token);
        }
        greaterCheck(userRole, minAuthRole);
        return null;
    }

    private void greaterCheck(Role userRole, Role minRole) {
        if (userRole.toValue() < minRole.toValue()) {
            throw new UnauthorizedException(NOT_AUTHORIZED_FOR_OPERATION);
        }
    }
}
