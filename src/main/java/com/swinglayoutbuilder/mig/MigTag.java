package com.swinglayoutbuilder.mig;

public enum MigTag {
    /**
     * An OK button.
     */
    OK("ok"),

    /**
     * A Cancel button.
     */
    CANCEL("cancel"),

    /**
     * Help button that is normally on the right.
     */
    HELP("help"),

    /**
     * Help button that on some platforms is placed to the left.
     */
    HELP2("help2"),

    /**
     * A Yes button.
     */
    YES("yes"),

    /**
     * A No button.
     */
    NO("no"),

    /**
     * An Apply button.
     */
    APPLY("apply"),

    /**
     * A Next or Forward button.
     */
    NEXT("next"),

    /**
     * A Previous or Back button.
     */
    BACK("back"),

    /**
     * A Finished button.
     */
    FINISH("finish"),

    /**
     * A button that should normally always be placed on the far left.
     */
    LEFT("left"),

    /**
     * A button that should normally always be placed on the far right.
     */
    RIGHT("right");

    private final String tag;

    MigTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
