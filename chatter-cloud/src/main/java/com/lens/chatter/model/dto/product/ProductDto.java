package com.lens.chatter.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 13 Nis 2020
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String productionNo;

    private ZonedDateTime productionTime;

    private UUID productTypeId;

    @Nullable
    private Set<UUID> defectIds;

    private Integer shift;

    private UUID branchId;
}
