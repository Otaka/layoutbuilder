package com.swinglayoutbuilder;

import javax.swing.*;
import java.awt.Container;

public class SwingLayoutBuilder {
    private final Container container;
    public static final String PARENT = "ConstraintLayoutBuilder_PARENT";

    public SwingLayoutBuilder(Container container) {
        if (container instanceof RootPaneContainer) {
            container = ((RootPaneContainer) container).getContentPane();
        }
        this.container = container;
    }

    public RowWithWrappingBuilder rowWithWrappingLayout() {
        return new RowWithWrappingBuilder(container);
    }

    public AttachToEdgesBuilder attachToEdgesLayout() {
        return new AttachToEdgesBuilder(container);
    }

    public ConstraintLayoutBuilder constraintLayout() {
        return new ConstraintLayoutBuilder(container);
    }

    public SimpleFormLayoutBuilder simpleFormLayout() {
        return new SimpleFormLayoutBuilder(container);
    }
}