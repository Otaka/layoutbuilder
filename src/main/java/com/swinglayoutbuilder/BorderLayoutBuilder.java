package com.swinglayoutbuilder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

public class BorderLayoutBuilder extends AbstractBuilder<BorderLayoutBuilder> {
    private final BorderLayout layout;
    private Component currentComponent;

    protected BorderLayoutBuilder(Container container) {
        super(container);
        layout = new BorderLayout(0, 0);
        container.setLayout(layout);
        currentComponent=container;
    }

    public BorderLayoutBuilder gapBetweenComponents(int hor, int ver) {
        layout.setHgap(hor);
        layout.setVgap(ver);
        return this;
    }

    public BorderLayoutBuilder addToCenter(Component component) {
        add(container, component, BorderLayout.CENTER);
        currentComponent = component;
        return this;
    }

    public BorderLayoutBuilder addToLeft(Component component) {
        add(container, component, BorderLayout.WEST);
        currentComponent = component;
        return this;
    }

    public BorderLayoutBuilder addToRight(Component component) {
        add(container, component, BorderLayout.EAST);
        currentComponent = component;
        return this;
    }

    public BorderLayoutBuilder addToTop(Component component) {
        add(container, component, BorderLayout.NORTH);
        currentComponent = component;
        return this;
    }

    public BorderLayoutBuilder addToBottom(Component component) {
        add(container, component, BorderLayout.SOUTH);
        currentComponent = component;
        return this;
    }

    /**
     * Set preferred size of last added component
     */
    public BorderLayoutBuilder preferredSize(int width, int height) {
        currentComponent.setPreferredSize(new Dimension(width, height));
        return this;
    }

    /**
     * Set minimum size of last added component
     */
    public BorderLayoutBuilder minimumSize(int width, int height) {
        currentComponent.setMinimumSize(new Dimension(width, height));
        return this;
    }

    /**
     * Set maximum size of last added component
     */
    public BorderLayoutBuilder maximumSize(int width, int height) {
        currentComponent.setMaximumSize(new Dimension(width, height));
        return this;
    }

    public Container finish() {
        return container;
    }
}