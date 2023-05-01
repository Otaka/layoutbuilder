package com.swinglayoutbuilder;

import javax.swing.RootPaneContainer;
import java.awt.Component;
import java.awt.Container;

public class SwingLayoutBuilder {
    private final Container container;
    public static final String PARENT = "RuleLayoutBuilder_PARENT";

    public SwingLayoutBuilder(Container container) {
        if (container instanceof RootPaneContainer) {
            container = ((RootPaneContainer) container).getContentPane();
        }
        this.container = container;
    }

    public RowWithWrappingBuilder rowWithWrappingLayout() {
        return new RowWithWrappingBuilder(container);
    }

    public BorderLayoutBuilder borderLayout() {
        return new BorderLayoutBuilder(container);
    }

    public RuleLayoutBuilder<Component> ruleLayout() {
        return new RuleLayoutBuilder<>(container);
    }

    public FormLayoutBuilder formLayout() {
        return new FormLayoutBuilder(container);
    }
}