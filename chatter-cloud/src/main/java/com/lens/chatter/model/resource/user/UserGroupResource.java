package com.lens.chatter.model.resource.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Created by Emir Gökdemir
 * on 5 Nis 2020
 */

@Getter
@Setter
@NoArgsConstructor
public class UserGroupResource {
    private String name;
    private Set<MinimalUserResource> users;
}
