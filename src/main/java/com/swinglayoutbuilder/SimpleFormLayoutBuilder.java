package com.swinglayoutbuilder;

import com.swinglayoutbuilder.constraintlayout.Constraint;
import com.swinglayoutbuilder.constraintlayout.ConstraintLayout;
import com.swinglayoutbuilder.constraintlayout.ConstraintLayoutGroup;
import com.swinglayoutbuilder.constraintlayout.Edge;

import javax.swing.*;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Simple layout that puts components in two columns(label:field)
 */
public class SimpleFormLayoutBuilder extends AbstractBuilder {
    private boolean labelLeftAlignment = true;
    private final List<Pair> fieldPairs = new ArrayList<>();
    private int gap = 5;
    private int rowGap = 5;
    private Function<String, Component> labelFactory;

    protected SimpleFormLayoutBuilder(Container container) {
        super(container);
        labelFactory = JLabel::new;
    }

    /**
     * Function that accepts String labelTitle and returns label component
     */
    public SimpleFormLayoutBuilder labelFactory(Function<String, Component>labelFactoryFunction) {
        this.labelFactory = labelFactoryFunction;
        return this;
    }

    public SimpleFormLayoutBuilder gap(int gap) {
        this.gap = gap;
        return this;
    }

    public SimpleFormLayoutBuilder rowGap(int gap) {
        this.rowGap = gap;
        return this;
    }

    public SimpleFormLayoutBuilder labelsLeftAlignment() {
        labelLeftAlignment = true;
        return this;
    }

    public SimpleFormLayoutBuilder labelsRightAlignment() {
        labelLeftAlignment = false;
        return this;
    }

    public SimpleFormLayoutBuilder add(String labelTitle, Component component) {
        Component label = createLabel(labelTitle);
        return add(label, component);
    }

    public SimpleFormLayoutBuilder add(Component label, Component component) {
        fieldPairs.add(new Pair(label, component));
        return this;
    }

    private Component createLabel(String labelTitle) {
        return labelFactory.apply(labelTitle);
    }

    @Override
    public Container finish() {
        ConstraintLayout layout = new ConstraintLayout();
        container.setLayout(layout);
        ConstraintLayoutGroup labelsGroup = layout.createGroup();
        for (Pair pair : fieldPairs) {
            labelsGroup.addComponent(pair.label);
        }

        Component topComponent = container;
        Edge topComponentEdge = Edge.TOP;
        int currentRowGap = 3;
        for (Pair pair : fieldPairs) {
            container.add(pair.label);
            container.add(pair.field);
            if (labelLeftAlignment) {
                layout.addConstraint(new Constraint(Edge.LEFT, container, 0, Edge.LEFT, pair.label));
            } else {
                layout.addConstraint(new Constraint(Edge.RIGHT, labelsGroup, 0, Edge.RIGHT, pair.label));
            }
            layout.addConstraint(new Constraint(topComponentEdge, topComponent, currentRowGap, Edge.TOP, pair.label));

            layout.addConstraint(new Constraint(Edge.RIGHT, labelsGroup, gap, Edge.LEFT, pair.field));
            layout.addConstraint(new Constraint(Edge.BASELINE, pair.label, 0, Edge.BASELINE, pair.field));

            currentRowGap = rowGap;
            topComponent = pair.label;
            topComponentEdge = Edge.BOTTOM;
        }
        return super.finish();
    }

    private static class Pair {
        Component label;
        Component field;

        public Pair(Component label, Component field) {
            this.label = label;
            this.field = field;
        }
    }
}