package com.lens.chatter.model.resource.user;

import com.lens.chatter.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.UUID;

@Getter
@Setter
@Resource
@AllArgsConstructor
@NoArgsConstructor
public class LoginResource {

    private String token;

    private UUID userId;

    private Role role;

    private String name;

    private String surname;

    private String department;

    private String userFirmId;

}
