package com.swinglayoutbuilder.mig;

public enum MigHideMode {
    /**
     * Means that invisible components will be handled exactly as if they were visible.
     */
    Default(0),
    /**
     * The size of an invisible component will be set to 0, 0.
     */
    InvisibleComponentZeroSize(1),
    /**
     * The size of an invisible component will be set to 0, 0 and the gaps will also be set to 0 around it.
     */
    InvisibleComponentZeroSizeZeroGap(2),
    /**
     * Invisible components will not participate in the layout at all and it will for instance not take up a grid cell.
     */
    NotPresentInLayout(3);

    private final int ordinal;

    MigHideMode(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return ordinal;
    }
}
