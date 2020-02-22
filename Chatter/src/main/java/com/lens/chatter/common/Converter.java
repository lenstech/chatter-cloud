package com.lens.chatter.common;

import java.util.UUID;

/**
 * DTO-Entity-Resource Converter Interface.
 */
public interface Converter<DTO, Entity, Resource> {

    Resource toResource(Entity entity);

    Entity toEntity(DTO dto);
}
