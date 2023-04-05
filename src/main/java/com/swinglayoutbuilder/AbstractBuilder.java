package com.swinglayoutbuilder;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractBuilder<T> {
    protected final Container container;
    private boolean debug;

    protected AbstractBuilder(Container container) {
        this.container = container;
    }

    public Container finish() {
        return container;
    }

    public T debug(boolean debug) {
        this.debug = debug;
        return (T) this;
    }

    protected void add(Container parent, Component component, Object constraint) {
        parent.add(component, constraint);
        if (debug) {
            paintComponent(component);
        }
    }

    protected void paintComponent(Component component) {
        if (component instanceof JComponent) {
            JComponent jcomponent = (JComponent) component;
            jcomponent.setOpaque(true);
            jcomponent.setBackground(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        }
    }
}