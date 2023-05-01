package com.swinglayoutbuilder;

import com.swinglayoutbuilder.rulelayout.*;

import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class RuleLayoutBuilder<T> extends AbstractBuilder<RuleLayoutBuilder<T>> {
    private ComponentWrapper<T> container;
    private ComponentWrapper<T> previousComponent;
    private ComponentWrapper<T> currentComponent;
    private ComponentWrapper<T> anchor1Component;
    private ComponentWrapper<T> anchor2Component;
    private ComponentWrapper<T> anchor3Component;
    private LayoutGroup<T> currentGroup;
    private final Map<String, ComponentWrapper<T>> id2ComponentMap = new HashMap<>();
    private final RuleLayout layout;


    protected RuleLayoutBuilder(Container container) {
        super(container);
        this.container = new ComponentWrapper<T>((T) container);
        layout = new RuleLayout(container);
        container.setLayout(layout);
        currentComponent = this.container;
    }

    public RuleLayoutBuilder<T> createGroup(String id) {
        LayoutGroup<T> group = (LayoutGroup<T>) layout.createGroup();
        if (id2ComponentMap.containsKey(id)) {
            throw new IllegalArgumentException("Component/group with id [" + id + "] is already present in builder");
        }

        currentGroup = group;
        id2ComponentMap.put(id, new ComponentWrapper<>(currentGroup));
        return this;
    }

    /**
     * Add current component to current group
     */
    public RuleLayoutBuilder<T> addToCurrentGroup() {
        checkCurrentComponent();
        checkCurrentGroup();
        currentGroup.addComponent(currentComponent);
        return this;
    }

    /**
     * Add component with id componentId to group with id groupId
     */
    public RuleLayoutBuilder<T> addToGroup(String componentId, String groupId) {
        ComponentWrapper<T> componentToAdd = getComponentById(componentId);
        ComponentWrapper<T> groupComponent = getComponentById(groupId);

        if (groupComponent.isGroup()) {
            LayoutGroup<T> group = groupComponent.getGroup();
            group.addComponent(componentToAdd);
        } else {
            throw new IllegalArgumentException("Component with id [" + groupId + "] is not group");
        }

        return this;
    }

    /**
     * Add current component to group with id groupId
     */
    public RuleLayoutBuilder<T> addToGroup(String groupId) {
        checkCurrentComponent();
        ComponentWrapper<T> groupComponent = getComponentById(groupId);
        if (groupComponent.isGroup()) {
            LayoutGroup<T> group = groupComponent.getGroup();
            group.addComponent(currentComponent);
        } else {
            throw new IllegalArgumentException("Component with id [" + groupId + "] is not group");
        }

        return this;
    }


    public RuleLayoutBuilder<T> parentPadding(int left, int top, int right, int bottom) {
        layout.setPadding(left, top, right, bottom);
        return this;
    }

    public RuleLayoutBuilder<T> id(String id) {
        checkCurrentComponent();
        if (id2ComponentMap.containsKey(id)) {
            throw new IllegalArgumentException("Component/group with id [" + id + "] is already present in builder");
        }

        id2ComponentMap.put(id, currentComponent);
        return this;
    }

    public RuleLayoutBuilder<T> anchor1() {
        checkCurrentComponent();
        anchor1Component = currentComponent;
        return this;
    }

    public RuleLayoutBuilder<T> anchor2() {
        checkCurrentComponent();
        anchor2Component = currentComponent;
        return this;
    }

    public RuleLayoutBuilder<T> anchor3() {
        checkCurrentComponent();
        anchor3Component = currentComponent;
        return this;
    }

    public RuleLayoutBuilder<T> setCurrentComponent(ComponentWrapper<T> component) {
        previousComponent = currentComponent;
        currentComponent = component;
        return this;
    }

    public RuleLayoutBuilder<T> add(T component) {
        add(null, component);
        return this;
    }

    public RuleLayoutBuilder<T> add(String id, T component) {
        if (!(component instanceof LayoutGroup)) {
            add((Container) container.getComponent(), (Component) component, null);
        }
        ComponentWrapper<T> c = new ComponentWrapper<T>(component);
        setCurrentComponent(c);
        if (id != null) {
            id(id);
        }
        return this;
    }

    public RuleLayoutBuilder<T> move(ComponentWrapper<T> component, Edge edge, String anchorComponentId, Edge anchorEdge, int gap) {
        layout.addRule(new Rule(anchorEdge, getComponentById(anchorComponentId), gap, edge, component));
        return this;
    }

    public RuleLayoutBuilder<T> move(ComponentWrapper<T> component, Edge edge, Object anchorComponent, Edge anchorEdge, int gap) {
        layout.addRule(new Rule(anchorEdge, anchorComponent, gap, edge, component));
        return this;
    }

    /**
     * Move parent edge to some other component edge by id
     */
    public RuleLayoutBuilder<T> moveParentToId(Edge edge, String anchorComponentId, Edge anchorEdge) {
        return move(container, edge, anchorComponentId, anchorEdge, 0);
    }

    /**
     * Move parent edge to other component edge by id
     */
    public RuleLayoutBuilder<T> moveParentToId(Edge edge, String anchorComponentId, Edge anchorEdge, int gap) {
        return move(container, edge, anchorComponentId, anchorEdge, gap);
    }


    /**
     * Move parent edge to current component edge
     */
    public RuleLayoutBuilder<T> moveParentToCurrent(Edge edge, Edge previousComponentEdge) {
        checkCurrentComponent();
        layout.addRule(new Rule(previousComponentEdge, currentComponent, 0, edge, container));
        return this;
    }

    /**
     * Move parent edge to current component edge
     */
    public RuleLayoutBuilder<T> moveParentToCurrent(Edge edge, Edge previousComponentEdge, int gap) {
        checkCurrentComponent();
        layout.addRule(new Rule(previousComponentEdge, currentComponent, gap, edge, container));
        return this;
    }

    /**
     * Move current component to other component with id anchorComponentId
     */
    public RuleLayoutBuilder<T> moveToId(Edge edge, String anchorComponentId, Edge anchorEdge) {
        checkCurrentComponent();
        return move(currentComponent, edge, anchorComponentId, anchorEdge, 0);
    }

    /**
     * Move current component to other component with id anchorComponentId
     */
    public RuleLayoutBuilder<T> moveToId(Edge edge, String anchorComponentId, Edge anchorEdge, int gap) {
        checkCurrentComponent();
        return move(currentComponent, edge, anchorComponentId, anchorEdge, gap);
    }

    /**
     * Move component with id componentId to other component with id anchorComponentId
     */
    public RuleLayoutBuilder<T> moveIdToId(String componentId, Edge edge, String anchorComponentId, Edge anchorEdge) {
        ComponentWrapper<T> component = getComponentById(componentId);
        return move(component, edge, anchorComponentId, anchorEdge, 0);
    }

    /**
     * Move component with id componentId to other component with id anchorComponentId
     */
    public RuleLayoutBuilder<T> moveIdToId(String componentId, Edge edge, String anchorComponentId, Edge anchorEdge, int gap) {
        ComponentWrapper<T> component = getComponentById(componentId);
        return move(component, edge, anchorComponentId, anchorEdge, gap);
    }

    /**
     * Move current component to parent component
     */
    public RuleLayoutBuilder<T> moveToParent(Edge edge, Edge parentEdge) {
        checkCurrentComponent();
        layout.addRule(new Rule(parentEdge, container, 0, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to parent component
     */
    public RuleLayoutBuilder<T> moveToParent(Edge edge, Edge parentEdge, int gap) {
        checkCurrentComponent();
        layout.addRule(new Rule(parentEdge, container, gap, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to parent component
     */
    public RuleLayoutBuilder<T> moveIdToParent(String componentId, Edge edge, Edge parentEdge, int gap) {
        ComponentWrapper<T> component = getComponentById(componentId);
        layout.addRule(new Rule(parentEdge, container, gap, edge, component));
        return this;
    }

    /**
     * Move component with id componentId to parent component
     */
    public RuleLayoutBuilder<T> moveIdToParent(String componentId, Edge edge, Edge parentEdge) {
        ComponentWrapper<T> component = getComponentById(componentId);
        layout.addRule(new Rule(parentEdge, container, 0, edge, component));
        return this;
    }

    /**
     * Move current component to previous component
     */
    public RuleLayoutBuilder<T> moveToPrevious(Edge edge, Edge previousComponentEdge) {
        checkCurrentComponent();
        checkPreviousComponent();
        layout.addRule(new Rule(previousComponentEdge, previousComponent, 0, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to previous component
     */
    public RuleLayoutBuilder<T> moveToPrevious(Edge edge, Edge previousComponentEdge, int gap) {
        checkCurrentComponent();
        checkPreviousComponent();
        layout.addRule(new Rule(previousComponentEdge, previousComponent, gap, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to anchor1 component
     */
    public RuleLayoutBuilder<T> moveToAnchor1(Edge edge, Edge anchor1ComponentEdge) {
        checkCurrentComponent();
        checkAnchor1Component();
        layout.addRule(new Rule(anchor1ComponentEdge, anchor1Component, 0, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to anchor1 component
     */
    public RuleLayoutBuilder<T> moveToAnchor1(Edge edge, Edge anchor1ComponentEdge, int gap) {
        checkCurrentComponent();
        checkAnchor1Component();
        layout.addRule(new Rule(anchor1ComponentEdge, anchor1Component, gap, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to anchor2 component
     */
    public RuleLayoutBuilder<T> moveToAnchor2(Edge edge, Edge anchor2ComponentEdge) {
        checkCurrentComponent();
        checkAnchor2Component();
        layout.addRule(new Rule(anchor2ComponentEdge, anchor2Component, 0, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to anchor2 component
     */
    public RuleLayoutBuilder<T> moveToAnchor2(Edge edge, Edge anchor2ComponentEdge, int gap) {
        checkCurrentComponent();
        checkAnchor2Component();
        layout.addRule(new Rule(anchor2ComponentEdge, anchor2Component, gap, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to anchor3 component
     */
    public RuleLayoutBuilder<T> moveToAnchor3(Edge edge, Edge anchor3ComponentEdge) {
        checkCurrentComponent();
        checkAnchor3Component();
        layout.addRule(new Rule(anchor3ComponentEdge, anchor3Component, 0, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to anchor3 component
     */
    public RuleLayoutBuilder<T> moveToAnchor3(Edge edge, Edge anchor3ComponentEdge, int gap) {
        checkCurrentComponent();
        checkAnchor3Component();
        layout.addRule(new Rule(anchor3ComponentEdge, anchor3Component, gap, edge, currentComponent));
        return this;
    }

    /**
     * Set preferred size of last added component, or for parent in case if no components are added yet
     */
    public RuleLayoutBuilder<T> preferredSize(int width, int height) {
        if (!currentComponent.isGroup()) {
            layout.setPreferredSize(currentComponent.getComponent(), new Dimension(width, height));
        }
        return this;
    }

    /**
     * Set minimum size of last added component
     */
    public RuleLayoutBuilder<T> minimumSize(int width, int height) {
        if (!currentComponent.isGroup()) {
            layout.setMinimumSize(currentComponent.getComponent(), new Dimension(width, height));
        }
        return this;
    }

    public RuleLayoutBuilder<T> anchorCurrentComponentEdgesToParentMovingEdges(boolean left, boolean top, boolean right, boolean bottom) {
        checkCurrentComponent();
        layout.setComponentEdgeAnchoredToParentMovingEdge(currentComponent, left, top, right, bottom, 1, 1, 1, 1);
        return this;
    }

    public RuleLayoutBuilder<T> anchorCurrentComponentEdgesToParentMovingEdges(boolean left, boolean top, boolean right, boolean bottom, float leftMultiplier, float topMultiplier, float rightMultiplier, float bottomMultiplier) {
        checkCurrentComponent();
        layout.setComponentEdgeAnchoredToParentMovingEdge(currentComponent, left, top, right, bottom, leftMultiplier, topMultiplier, rightMultiplier, bottomMultiplier);
        return this;
    }

    public RuleLayoutBuilder<T> anchorComponentIdEdgesToParentMovingEdges(String componentId, boolean left, boolean top, boolean right, boolean bottom) {
        layout.setComponentEdgeAnchoredToParentMovingEdge(getComponentById(componentId), left, top, right, bottom, 1, 1, 1, 1);
        return this;
    }

    public RuleLayoutBuilder<T> anchorComponentIdEdgesToParentMovingEdges(String componentId, boolean left, boolean top, boolean right, boolean bottom, float leftMultiplier, float topMultiplier, float rightMultiplier, float bottomMultiplier) {
        layout.setComponentEdgeAnchoredToParentMovingEdge(getComponentById(componentId), left, top, right, bottom, leftMultiplier, topMultiplier, rightMultiplier, bottomMultiplier);
        return this;
    }

    public RuleLayoutBuilder<T> templateForm(int labelToComponentDistance, FormTemplateProvider<T> templateInitializer) {
        FormTemplate<T> formTemplate = new FormTemplate<T>(this, labelToComponentDistance);
        templateInitializer.run(formTemplate, this);
        formTemplate.finish();
        return this;
    }

    public interface FormTemplateProvider<T> {
        void run(FormTemplate<T> formTemplate, RuleLayoutBuilder<T> ruleLayoutBuilder);
    }

    public static class FormTemplate<T> {
        RuleLayoutBuilder<T> layoutBuilder;
        String formGroupId;
        String labelGroupId;
        String fieldGroupId;
        int labelToComponentDistance;
        int rowsGap = 10;
        int rowIndex = 0;
        private boolean alignLabelsLeft = true;
        private final List<Row> rows = new ArrayList<>();

        public FormTemplate(RuleLayoutBuilder<T> layoutBuilder, int labelToComponentDistance) {
            this.labelToComponentDistance = labelToComponentDistance;
            this.layoutBuilder = layoutBuilder;
        }

        public void setLabelToComponentDistance(int labelToComponentDistance) {
            this.labelToComponentDistance = labelToComponentDistance;
        }

        public void setRowsGap(int rowsGap) {
            this.rowsGap = rowsGap;
        }

        private String getFormGroupId() {
            if (formGroupId == null) {
                formGroupId = "FormGroup" + Math.random() * 99999;
            }
            if (!layoutBuilder.id2ComponentMap.containsKey(formGroupId)) {
                layoutBuilder.createGroup(formGroupId);
            }
            return formGroupId;
        }

        private void setFormGroupId(String formGroupId) {
            if (this.formGroupId != null)
                throw new IllegalStateException("formGroupId is already set: " + this.formGroupId);

            this.formGroupId = formGroupId;
        }

        private String getLabelGroupId() {
            if (labelGroupId == null) {
                labelGroupId = "FormLabelsGroup" + Math.random() * 99999;
            }
            if (!layoutBuilder.id2ComponentMap.containsKey(labelGroupId)) {
                layoutBuilder.createGroup(labelGroupId);
            }
            return labelGroupId;
        }

        private void setLabelGroupId(String groupId) {
            if (this.labelGroupId != null)
                throw new IllegalStateException("labelGroupId is already set: " + this.labelGroupId);

            this.labelGroupId = groupId;
        }

        private String getFieldGroupId() {
            if (fieldGroupId == null) {
                fieldGroupId = "FormFieldGroup" + Math.random() * 99999;
            }
            if (!layoutBuilder.id2ComponentMap.containsKey(fieldGroupId)) {
                layoutBuilder.createGroup(fieldGroupId);
            }
            return fieldGroupId;
        }

        private void setFieldGroupId(String groupId) {
            if (this.fieldGroupId != null)
                throw new IllegalStateException("fieldGroupId is already set: " + this.fieldGroupId);
            this.fieldGroupId = groupId;
        }


        public void setAlignLabelsLeft(boolean alignLabelsLeft) {
            this.alignLabelsLeft = alignLabelsLeft;
        }

        public Row addRow(String label, T field) {
            String rowGroup = "formRowGroup" + rowIndex;
            layoutBuilder.createGroup(rowGroup);

            T labelComponent = createLabel(layoutBuilder.container, label);
            layoutBuilder.add(labelComponent).addToGroup(getFormGroupId()).addToGroup(getLabelGroupId()).addToGroup(rowGroup);
            layoutBuilder.add(field).addToGroup(getFormGroupId()).addToGroup(getFieldGroupId()).addToGroup(rowGroup);
            Row row = new Row(rowGroup, new ComponentWrapper<>(labelComponent), new ComponentWrapper<>(field));
            rows.add(row);
            rowIndex++;
            return row;
        }

        private T createLabel(ComponentWrapper<T> container, String text) {
            return (T) new JLabel(text);
        }

        public void finish() {
            //first - align labels and fields horizontally
            for (Row row : rows) {
                if (alignLabelsLeft) {
                    layoutBuilder.move(row.label, Edge.LEFT, layoutBuilder.id2ComponentMap.get(getLabelGroupId()), Edge.LEFT, 0);
                } else {
                    layoutBuilder.move(row.label, Edge.RIGHT, layoutBuilder.id2ComponentMap.get(getLabelGroupId()), Edge.RIGHT, 0);
                }
                layoutBuilder.move(row.field, Edge.LEFT, layoutBuilder.id2ComponentMap.get(getLabelGroupId()), Edge.RIGHT, labelToComponentDistance);
            }
            //align labels to fields
            for (Row row : rows) {
                switch (row.alignment) {
                    case BASELINE:
                        layoutBuilder.move(row.label, Edge.BASELINE, row.field, Edge.BASELINE, 0);
                        break;
                    case TOP:
                        layoutBuilder.move(row.label, Edge.TOP, row.field, Edge.TOP, 0);
                        break;
                    case BOTTOM:
                        layoutBuilder.move(row.label, Edge.BOTTOM, row.field, Edge.BOTTOM, 0);
                        break;
                    case CENTER:
                        layoutBuilder.move(row.label, Edge.VER_CENTER, row.field, Edge.VER_CENTER, 0);
                }

            }
            //place groups each after other
            Row previousRow = null;
            for (Row row : rows) {
                if (previousRow != null) {//we ignore first field, because it is already on top
                    layoutBuilder.move(layoutBuilder.getComponentById(row.rowGroupId), Edge.TOP, layoutBuilder.getComponentById(previousRow.rowGroupId), Edge.BOTTOM, rowsGap);
                }
                previousRow = row;
            }
            layoutBuilder.setCurrentComponent(layoutBuilder.getComponentById(getFormGroupId()));
        }

        public class Row {
            String rowGroupId;
            ComponentWrapper<T> label;
            ComponentWrapper<T> field;
            FormRowAlignment alignment = FormRowAlignment.BASELINE;

            protected Row(String rowGroupId, ComponentWrapper<T> label, ComponentWrapper<T> field) {
                this.rowGroupId = rowGroupId;
                this.label = label;
                this.field = field;
            }

            public Row setLabelToFieldVerticalAlignment(FormRowAlignment alignment) {
                this.alignment = alignment;
                return this;
            }

            public Row id(String id) {
                layoutBuilder.id2ComponentMap.put(id, field);
                return this;
            }

            public String getRowGroupId() {
                return rowGroupId;
            }

            public ComponentWrapper<T> getLabel() {
                return label;
            }

            public ComponentWrapper<T> getField() {
                return field;
            }
        }
    }

    public enum FormRowAlignment {
        TOP, BOTTOM, CENTER, BASELINE;
    }

    private ComponentWrapper<T> getComponentById(String id) {
        if (id.equals(SwingLayoutBuilder.PARENT)) {
            return container;
        }

        if (!id2ComponentMap.containsKey(id)) {
            throw new IllegalArgumentException("Cannot find component with id=" + id);
        }
        return id2ComponentMap.get(id);
    }

    private void throwIfNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalStateException(message);
        }
    }

    private void checkCurrentComponent() {
        throwIfNull(currentComponent, "There is no current component");
    }

    private void checkPreviousComponent() {
        throwIfNull(previousComponent, "There is no previous component");
    }

    private void checkAnchor1Component() {
        throwIfNull(anchor1Component, "There is no anchor1 component");
    }

    private void checkAnchor2Component() {
        throwIfNull(anchor2Component, "There is no anchor2 component");
    }

    private void checkAnchor3Component() {
        throwIfNull(anchor3Component, "There is no anchor3 component");
    }

    private void checkCurrentGroup() {
        throwIfNull(currentGroup, "There is no current group");
    }

    @Override
    public RuleLayoutBuilder<T> exec(Consumer currentComponentProcessor) {
        currentComponentProcessor.process((Component) currentComponent.getComponent());
        return this;
    }
}