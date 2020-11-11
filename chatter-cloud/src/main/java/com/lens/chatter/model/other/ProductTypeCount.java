package com.lens.chatter.model.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 11 Kas 2020
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductTypeCount {


    private UUID productTypeId;
    private String productTypeName;
    private String productTypeDescription;
    private String productTypeColor;
    private Long count;
    private float rate;

}
