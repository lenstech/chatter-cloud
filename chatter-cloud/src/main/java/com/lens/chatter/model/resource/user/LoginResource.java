package com.lens.chatter.model.resource.user;

import com.lens.chatter.constant.Role;
import com.lens.chatter.model.entity.Department;
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
public class LoginResource extends CompleteUserResource{

    private String token;
}
