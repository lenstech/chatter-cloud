package com.lens.chatter.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by Emir Gökdemir
 * on 11 Nis 2020
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordDto {
    @NotNull(message = "OldPassword should be assign")
    private String oldPassword;
    @NotNull(message = "NewPassword should be assign")
    private String newPassword;
}
