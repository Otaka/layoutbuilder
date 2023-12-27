package com.swinglayoutbuilder;

import com.swinglayoutbuilder.mig.MigLayoutBuilder;

import javax.swing.RootPaneContainer;
import java.awt.Component;
import java.awt.Container;

public class SwingLayoutBuilder {
    public static final String PARENT = "RuleLayoutBuilder_PARENT";
    private final Container container;

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

    public MigLayoutBuilder migLayout() {
        try {
            Class.forName("net.miginfocom.swing.MigLayout");
        } catch (Exception ex) {
            throw new RuntimeException("MigLayout dependency is not in classpath. Please add it.");
        }
        MigLayoutBuilder builder = new MigLayoutBuilder();
        builder.setContainer(container);
        return builder;
    }
}