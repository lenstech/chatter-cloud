package com.lens.chatter.model.resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 23 Şub 2020
 */
@Getter
@Setter
@NoArgsConstructor
public class DepartmentResource {

    private UUID id;

    private ZonedDateTime createdDate;

    private String name;

    private String description;
}
