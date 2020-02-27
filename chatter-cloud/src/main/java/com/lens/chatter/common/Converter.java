package com.lens.chatter.common;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * DTO-Entity-Resource Converter Interface.
 */
@Component
public interface Converter<DTO, Entity, Resource> {

    Resource toResource(Entity entity);

    Entity toEntity(DTO dto);

    List<Resource> toResources(List<Entity> entities);
}
