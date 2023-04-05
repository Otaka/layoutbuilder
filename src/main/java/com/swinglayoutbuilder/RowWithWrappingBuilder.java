package com.swinglayoutbuilder;

import javax.swing.*;
import java.awt.*;

public class RowWithWrappingBuilder extends AbstractBuilder<RowWithWrappingBuilder> {

    private final FlowLayout layout;
    private Component currentComponent;

    public RowWithWrappingBuilder(Container container) {
        super(container);
        layout = new FlowLayout(FlowLayout.LEFT);
        layout.setHgap(0);
        layout.setVgap(0);
        container.setLayout(layout);
        currentComponent = container;
    }

    public RowWithWrappingBuilder componentPlacementDirection(ComponentOrientation direction) {
        container.setComponentOrientation(direction);
        return this;
    }

    public RowWithWrappingBuilder baseLineAlignment(boolean enabled) {
        layout.setAlignOnBaseline(enabled);
        return this;
    }

    public RowWithWrappingBuilder gapBetweenComponents(int hor, int ver) {
        layout.setHgap(hor);
        layout.setVgap(ver);
        return this;
    }

    public RowWithWrappingBuilder rowAlignmentCenter() {
        layout.setAlignment(FlowLayout.CENTER);
        return this;
    }

    public RowWithWrappingBuilder rowAlignmentLeft() {
        layout.setAlignment(FlowLayout.LEFT);
        return this;
    }

    public RowWithWrappingBuilder rowAlignmentRight() {
        layout.setAlignment(FlowLayout.RIGHT);
        return this;
    }

    public RowWithWrappingBuilder add(JComponent component) {
        add(container, component, null);
        currentComponent = component;
        return this;
    }

    /**
     * Set preferred size of last added component
     */
    public RowWithWrappingBuilder preferredSize(int width, int height) {
        currentComponent.setPreferredSize(new Dimension(width, height));
        return this;
    }

    /**
     * Set minimum size of last added component
     */
    public RowWithWrappingBuilder minimumSize(int width, int height) {
        currentComponent.setMinimumSize(new Dimension(width, height));
        return this;
    }

    /**
     * Set maximum size of last added component
     */
    public RowWithWrappingBuilder maximumSize(int width, int height) {
        currentComponent.setMaximumSize(new Dimension(width, height));
        return this;
    }
}