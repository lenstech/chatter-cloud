package com.lens.chatter.model.dto.user;

import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 11 Şub 2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    @Email(message = ErrorConstants.PROVIDE_VALID_MAIL)
    private String email;

    @NotNull(message = "Name should be assign")
    private String name;

    @NotNull(message = "Name should be assign")
    private String surname;

    private Role role;

    private UUID departmentId;

    @NotNull(message = "FirmId should be assign")
    private String userFirmId;

    private String title;

    @NotNull(message = "Password should be assign")
    private String password;
}
