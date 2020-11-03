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
    private String startTime;
    private String endTime;
}
