package com.lens.chatter.model.resource.user;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;

@Getter
@Setter
@Resource
public class CompleteUserResource extends MinimalUserResource {

    private String email;

    private String department;


}
