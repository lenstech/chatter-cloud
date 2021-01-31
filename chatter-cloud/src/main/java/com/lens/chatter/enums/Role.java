package com.lens.chatter.enums;

public enum Role {
    NOT_AUTH(0),
    BASIC_USER(1),
    DEPARTMENT_ADMIN(2),
    BRANCH_ADMIN(3),
    FIRM_ADMIN(4),
    ADMIN(5);  //the developer mode

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int toValue() {
        return value;
    }
}
