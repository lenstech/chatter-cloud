package com.lens.chatter.model.dto.user;

import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 15 Eki 2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InviteMailDto {
    @Email(message = ErrorConstants.PROVIDE_VALID_MAIL)
    private String mail;
    private String title = "-";
    private Role role = Role.BASIC_USER;
    private UUID departmentId;
}
