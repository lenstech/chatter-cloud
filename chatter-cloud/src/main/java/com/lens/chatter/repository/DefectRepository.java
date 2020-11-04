package com.lens.chatter.repository;


import com.lens.chatter.model.entity.Defect;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DefectRepository extends ChatterRepository<Defect, UUID> {

}
