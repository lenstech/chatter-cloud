package com.lens.chatter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Created by Emir Gökdemir
 * on 24 Şub 2020
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends ResponseStatusException {

    public UnauthorizedException(@Nullable String statusText) {
        super(HttpStatus.UNAUTHORIZED, statusText);
    }

}
