package com.lens.chatter.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
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

    @NotNull(message = "ProductionTime cannot be null")
    private ZonedDateTime productionTime;

    @NotNull(message = "productTypeId cannot be null")
    private UUID productTypeId;

    @Nullable
    private Set<UUID> defectIds;

    @NotNull(message = "shift cannot be null")
    private Integer shift;

    private UUID branchId;
}
