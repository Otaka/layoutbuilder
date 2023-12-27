package com.swinglayoutbuilder.mig;

import com.swinglayoutbuilder.AbstractBuilder;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Container;


class MigLayoutBuilderBase extends AbstractBuilder<MigLayoutBuilder> {
    protected final StringBuilder layoutConstraints = new StringBuilder();
    protected final StringBuilder rowConstraints = new StringBuilder();
    protected final StringBuilder columnConstraints = new StringBuilder();
    protected Component currentComponent;
    protected StringBuilder componentConstraints = new StringBuilder();
    private MigLayout layout;

    public MigLayoutBuilderBase() {
        super(null);
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    /**
     * add JLabel component
     */
    public MigLayoutBuilder label(String text) {
        add(new JLabel(text));
        return (MigLayoutBuilder) this;
    }

    public MigLayoutBuilder add(Component component) {
        checkLayoutCreated();
        if (currentComponent != null) {
            container.add(currentComponent, componentConstraints.toString());
        }
        componentConstraints = new StringBuilder();
        currentComponent = component;
        return (MigLayoutBuilder) this;
    }

    protected void checkLayoutCreated() {
        if (layout == null) {
            layout = new MigLayout(layoutConstraints.toString(), columnConstraints.toString(), rowConstraints.toString());
            container.setLayout(layout);
        }
    }

    protected void checkLayoutAlreadyInitialized() {
        if (layout != null) {
            throw new RuntimeException("Cannot set layout properties, because MigLayout already initialized. You can modify layout/row/column configuration only before adding actual components");
        }
    }

    @Override
    public Container finish() {
        if (currentComponent != null) {
            container.add(currentComponent, componentConstraints.toString());
        }
        return super.finish();
    }
}
