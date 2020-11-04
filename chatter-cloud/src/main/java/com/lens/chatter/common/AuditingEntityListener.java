package com.lens.chatter.common;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by EmirGökdemir on 11/11/2019.
 */
@Component
public class AuditingEntityListener<ID extends Serializable> {

    @PrePersist
    @PreUpdate
    public void setLastModifiedDate(AbstractEntity<ID> entity) {
        if (StringUtils.isEmpty(entity.getId())) {
            entity.setCreatedDate(ZonedDateTime.now(ZoneId.systemDefault()));
        }
        entity.setLastModifiedDate(ZonedDateTime.now(ZoneId.systemDefault()));
    }
}
