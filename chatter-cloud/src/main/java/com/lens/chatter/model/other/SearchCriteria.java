package com.lens.chatter.model.other;

import com.lens.chatter.enums.SearchOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Emir Gökdemir
 * on 2 May 2020
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchCriteria {
    private String key;
    private Object value;
    private SearchOperator operation;
    private String startTime = "1970-01-01 00:00:00";
    private String endTime = "2100-01-01 00:00:00";
}
