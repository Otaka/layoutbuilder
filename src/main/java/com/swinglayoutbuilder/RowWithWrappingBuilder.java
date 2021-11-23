package com.swinglayoutbuilder;

import javax.swing.*;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;

public class RowWithWrappingBuilder extends AbstractBuilder {

    private final FlowLayout layout;

    public RowWithWrappingBuilder(Container container) {
        super(container);
        layout = new FlowLayout(FlowLayout.LEADING);
        container.setLayout(layout);
    }

    public RowWithWrappingBuilder reverseOrder(boolean enabled) {
        container.setComponentOrientation(enabled ? ComponentOrientation.RIGHT_TO_LEFT : ComponentOrientation.LEFT_TO_RIGHT);
        return this;
    }

    public RowWithWrappingBuilder baseLineAlignment(boolean enabled) {
        layout.setAlignOnBaseline(enabled);
        return this;
    }

    public RowWithWrappingBuilder gap(int hor, int ver) {
        layout.setHgap(hor);
        layout.setVgap(ver);
        return this;
    }

    public RowWithWrappingBuilder alignmentCenter() {
        layout.setAlignment(FlowLayout.CENTER);
        return this;
    }

    public RowWithWrappingBuilder alignmentLeft() {
        layout.setAlignment(FlowLayout.LEFT);
        return this;
    }

    public RowWithWrappingBuilder alignmentRight() {
        layout.setAlignment(FlowLayout.RIGHT);
        return this;
    }

    public RowWithWrappingBuilder add(JComponent component) {
        container.add(component);
        return this;
    }
}