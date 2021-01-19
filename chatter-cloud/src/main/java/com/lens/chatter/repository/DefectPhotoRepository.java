package com.lens.chatter.repository;

import com.lens.chatter.model.entity.DefectPhoto;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DefectPhotoRepository extends ChatterRepository<DefectPhoto, UUID> {

    boolean existsByDefectId(UUID DefectId);

    Optional<DefectPhoto> findDefectPhotoByDefectId(UUID DefectId);

    void deleteDefectPhotoByDefectId(UUID DefectId);
}
