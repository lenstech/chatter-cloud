package com.lens.chatter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ChannelType {
    DEPARTMENT("Department", "d"),
    BRANCH("Branch", "b"),
    FIRM("Firm", "f");

    private final String value;
    private final String initial;

    public String toValue() {
        return value;
    }
}
