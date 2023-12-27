package com.swinglayoutbuilder.mig;

public enum MigAlign {
    TOP("top"), LEFT("left"), RIGHT("right"), BOTTOM("bottom"),
    /**
     * Leading/trailing is dependant on if component orientation is "left-to-right" or "right-to-left"
     */
    LEAD("lead"),
    /**
     * Leading/trailing is dependant on if component orientation is "left-to-right" or "right-to-left"
     */
    TRAIL("trail"),
    /**
     * Baseline defaults to center alignment if not supported by the JDK. Components in the same grid row can be baseline aligned
     */
    BASELINE("baseline"),
    /**
     * It will align the component(s), which is normally labels, left, center or right depending on the style guides for the platform. This currently means left justfied on all platforms except OS X which has right justified labels.
     */
    LABEL("label"),
    ;
    private final String value;

    MigAlign(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
