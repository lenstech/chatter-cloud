package com.lens.chatter.model.dto.user;

import com.lens.chatter.constant.ErrorConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

/**
 * Created by Emir Gökdemir
 * on 11 Nis 2020
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @Email(message = ErrorConstants.PROVIDE_VALID_MAIL)
    private String email;

    private String password;

    private String firebaseToken;
}
