package com.lens.chatter.model.other;

import com.lens.chatter.model.entity.DefectType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Created by Emir Gökdemir
 * on 11 Kas 2020
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DefectTypeCount {

    private DefectType defectType;
    private Long count;
    private float rate;

}
