package com.swinglayoutbuilder.mig;

public enum MigUnits {
    /**
     * Pixels. Normal pixels mapped directly to the screen. E.g. "10px" or "10"
     */
    Pixels,
    /**
     * A percentage of the container's size. May also be used for alignments where for instance 50% means "centered". E.g. "100%"
     */
    Percent,
    /**
     * Logical Pixels. If the normal font is used on the platform this maps 1:1 to pixels. If larger fonts are used the logical pixels gets proportionally larger. E.g. "10lp"
     */
    LogicalPixels,
    /**
     * Points. 1/72:th of an inch. Normally used for printing. Will take the screen DPI that the component is showing on into account. E.g. "10pt"
     */
    Points,
    /**
     * Millimeters. Will take the screen DPI that the component is showing on into account. E.g. "10 mm"
     */
    Millimeters,
    /**
     * Centimeters. Will take the screen DPI that the component is showing on into account. E.g. "10 cm"
     */
    Centimeters,
    /**
     * Inches. Will take the screen DPI that the component is showing on into account. E.g. "10 in"
     */
    Inches,
    /**
     * Percentage of the screen. Will take the pixel screen size that the component is showing on into account. 100.0 is the right/bottom edge of the screen. E.g. "sp 70" or "sp 73.627123"
     */
    ScreenPercentage,
    /**
     * Visual bounds alignment. "0al" is left aligned, "0.5al" is centered and "1al" is right aligned. This unit is used with absolute positioning. E.g. "0.2al"
     */
    VisualBoundsAlignment
}
