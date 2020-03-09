package com.lens.chatter.common;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 29 Şub 2020
 */
@Getter
@Setter
public class AbstractResource {
    private UUID id;

    private ZonedDateTime createdDate;

    private String name;
}
