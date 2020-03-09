package com.lens.chatter.model.resource;

import com.lens.chatter.common.AbstractResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Emir Gökdemir
 * on 1 Mar 2020
 */

@Getter
@Setter
@NoArgsConstructor
public class FirmResource extends AbstractResource {

    private String city;

    private String taxId;
}
