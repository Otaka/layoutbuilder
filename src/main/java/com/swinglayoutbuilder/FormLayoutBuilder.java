package com.swinglayoutbuilder;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple layout that puts components in two columns(label:field)
 */
public class FormLayoutBuilder extends AbstractBuilder<FormLayoutBuilder> {
    private final List<List<Pair>> columns = new ArrayList<>();
    private boolean labelLeftAlignment = true;
    private List<Pair> currentColumn;
    private int gapLabelToComponent = 5;
    private int gapBetweenRows = 5;
    private int gapBetweenColumns = 20;
    private LabelFactory labelFactory;
    private Component currentComponent;

    protected FormLayoutBuilder(Container container) {
        super(container);
        labelFactory = new LabelFactory() {
            @Override
            public Component create(String text) {
                return new JLabel(text);
            }
        };
        currentComponent = container;
        newColumn();
    }

    public FormLayoutBuilder newColumn() {
        currentColumn = new ArrayList<>();
        columns.add(currentColumn);
        return this;
    }

    /**
     * Label factory that accepts String labelTitle and returns label component
     */
    public FormLayoutBuilder labelFactory(LabelFactory labelFactory) {
        this.labelFactory = labelFactory;
        return this;
    }

    public FormLayoutBuilder gapLabelToComponent(int gap) {
        this.gapLabelToComponent = gap;
        return this;
    }

    public FormLayoutBuilder gapBetweenRows(int gap) {
        this.gapBetweenRows = gap;
        return this;
    }

    public FormLayoutBuilder gapBetweenColumns(int gap) {
        this.gapBetweenColumns = gap;
        return this;
    }

    public FormLayoutBuilder labelsLeftAlignment() {
        labelLeftAlignment = true;
        return this;
    }

    public FormLayoutBuilder labelsRightAlignment() {
        labelLeftAlignment = false;
        return this;
    }

    public FormLayoutBuilder add(String labelTitle, Component component) {
        Component label = createLabel(labelTitle);
        currentComponent = component;
        return add(label, component);
    }

    public FormLayoutBuilder add(Component component) {
        currentComponent = component;
        return add((Component) null, component);
    }


    public FormLayoutBuilder add(Component label, Component component) {
        currentColumn.add(new Pair(label, component));
        return this;
    }

    /**
     * Set preferred size of last added component
     */
    public FormLayoutBuilder preferredSize(int width, int height) {
        currentComponent.setPreferredSize(new Dimension(width, height));
        return this;
    }

    /**
     * Set minimum size of last added component
     */
    public FormLayoutBuilder minimumSize(int width, int height) {
        currentComponent.setMinimumSize(new Dimension(width, height));
        return this;
    }

    /**
     * Set maximum size of last added component
     */
    public FormLayoutBuilder maximumSize(int width, int height) {
        currentComponent.setMaximumSize(new Dimension(width, height));
        return this;
    }

    private Component createLabel(String labelTitle) {
        return labelFactory.create(labelTitle);
    }

    @Override
    public Container finish() {
        GridBagLayout layout = new GridBagLayout();
        container.setLayout(layout);
        int column = 0;
        for (List<Pair> currentColumn : columns) {
            int row = 0;
            if (column != 0) {
                //add padding between columns
                JPanel paddingPanel = new JPanel(false);
                paddingPanel.setPreferredSize(new Dimension(gapBetweenColumns, 1));
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = (column * 3) - 1;
                c.gridy = 0;
                c.weightx = 0.1;

                c.fill = GridBagConstraints.BOTH;
                container.add(paddingPanel, c);
            }
            for (Pair pair : currentColumn) {
                if (pair.label != null) {
                    int rowTopInsets = row > 0 ? gapBetweenRows : 0;
                    GridBagConstraints c = new GridBagConstraints();
                    c.fill = GridBagConstraints.NONE;
                    c.gridx = column * 3;
                    c.gridy = row;
                    c.anchor = labelLeftAlignment ? GridBagConstraints.BASELINE_LEADING : GridBagConstraints.BASELINE_TRAILING;

                    c.insets = new Insets(rowTopInsets, 0, 0, gapLabelToComponent);
                    add(container, pair.label, c);

                    c = new GridBagConstraints();
                    c.gridx = column * 3 + 1;
                    c.gridy = row;
                    c.anchor = GridBagConstraints.BASELINE_LEADING;
                    c.insets = new Insets(rowTopInsets, 0, 0, 0);
                    c.fill = GridBagConstraints.NONE;
                    add(container, pair.field, c);
                } else {
                    GridBagConstraints c = new GridBagConstraints();
                    c.gridx = column * 3;
                    c.gridy = row;
                    c.gridwidth = 2;
                    c.anchor = GridBagConstraints.BASELINE_LEADING;
                    c.insets = new Insets(0, 0, 0, 0);
                    c.fill = GridBagConstraints.NONE;
                    add(container, pair.field, c);
                }
                row++;
            }
            column++;
        }

        return container;
    }

    public interface LabelFactory {
        Component create(String text);
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