package com.swinglayoutbuilder.mig;

public enum MigDock {
    NORTH("north"),
    SOUTH("south"),
    EAST("east"),
    WEST("west"),
    CENTER("center"),
    TOP("north"),
    BOTTOM("south"),
    LEFT("west"),
    RIGHT("east"),
    ;

    private final String value;

    MigDock(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
