package com.lens.chatter.model.dto;

import com.lens.chatter.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String email;

    private String name;

    private String surname;

    private Role role;

    private UUID departmentId;

    private String userFirmId;

    private String title;

}