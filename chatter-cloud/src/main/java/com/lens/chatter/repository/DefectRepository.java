package com.lens.chatter.repository;


import com.lens.chatter.model.entity.Defect;
import org.mapstruct.Named;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
public interface DefectRepository extends ChatterRepository<Defect, UUID> {

}
