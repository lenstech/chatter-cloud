package com.lens.chatter.utils;

import com.lens.chatter.model.entity.Defect;
import com.lens.chatter.repository.DefectRepository;
import com.lens.chatter.repository.ProductTypeRepository;
import lombok.experimental.UtilityClass;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 17 Nis 2020
 */
@UtilityClass
public class MapperUtils {
    @Autowired
    private DefectRepository defectRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Named("toDefect")
    public Set<Defect> toDefect(Set<UUID> defectIds) {
        if (defectIds == null) {
            return new HashSet<>();
        } else {
            return defectRepository.findByIdIn(defectIds);
        }
    }
}

