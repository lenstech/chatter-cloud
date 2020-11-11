package com.lens.chatter.repository;

import com.lens.chatter.model.entity.Defect;
import com.lens.chatter.model.other.DefectTypeCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface DefectRepository extends ChatterRepository<Defect, UUID> {

    Integer countDefectsByCreatedDateBetween(ZonedDateTime begin, ZonedDateTime end);

    @Query("select new com.lens.chatter.model.other.DefectTypeCount(d.defectType, count(d), (1.0f*count(d))/ ?3 ) " +
            " from Defect d " +
            " where d.createdDate between ?1 and ?2 " +
            " group by d.defectType.id ")
    List<DefectTypeCount> getStatisticsByDefectType(ZonedDateTime startTime, ZonedDateTime endTime, float total);
}

