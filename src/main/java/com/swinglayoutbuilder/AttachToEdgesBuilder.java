package com.swinglayoutbuilder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

public class AttachToEdgesBuilder extends AbstractBuilder {
    private BorderLayout layout;

    protected AttachToEdgesBuilder(Container container) {
        super(container);
        layout = new BorderLayout(0, 0);
        container.setLayout(layout);
    }

    public AttachToEdgesBuilder gap(int hor, int ver) {
        layout.setHgap(hor);
        layout.setVgap(ver);
        return this;
    }

    public AttachToEdgesBuilder addToCenter(Component component) {
        container.add(component);
        return this;
    }

    public AttachToEdgesBuilder addToLeft(Component component) {
        container.add(component, BorderLayout.WEST);
        return this;
    }

    public AttachToEdgesBuilder addToRight(Component component) {
        container.add(component, BorderLayout.EAST);
        return this;
    }

    public AttachToEdgesBuilder addToTop(Component component) {
        container.add(component, BorderLayout.NORTH);
        return this;
    }

    public AttachToEdgesBuilder addToBottom(Component component) {
        container.add(component, BorderLayout.SOUTH);
        return this;
    }
}