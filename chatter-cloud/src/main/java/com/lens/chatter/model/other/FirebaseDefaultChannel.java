package com.lens.chatter.model.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;
import java.util.List;

/**
 * Created by Emir Gökdemir
 * on 18 Kas 2020
 */

@Getter
@Setter
@AllArgsConstructor
public class FirebaseDefaultChannel {
    private String name;

    private String ownerId;

    private List<String> thread;
}
