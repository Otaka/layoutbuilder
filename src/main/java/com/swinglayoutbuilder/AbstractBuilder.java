package com.swinglayoutbuilder;

import java.awt.Container;

public abstract class AbstractBuilder {
    protected final Container container;

    protected AbstractBuilder(Container container) {
        this.container = container;
    }

    public Container finish() {
        return container;
    }
}