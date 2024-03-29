package com.lens.chatter.model.dto.product;

import com.lens.chatter.model.other.SearchCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Emir Gökdemir
 * on 2 Kas 2020
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {

    @NotNull(message = "Width should be assign")
    private List<SearchCriteria> filterCriteria; //map as (field: "value")

}
