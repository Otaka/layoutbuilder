package com.swinglayoutbuilder;

import com.swinglayoutbuilder.constraintlayout.Constraint;
import com.swinglayoutbuilder.constraintlayout.ConstraintLayout;
import com.swinglayoutbuilder.constraintlayout.ConstraintLayoutGroup;
import com.swinglayoutbuilder.constraintlayout.Edge;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

public class ConstraintLayoutBuilder extends AbstractBuilder {

    private Component previousComponent;
    private Component currentComponent;
    private Component anchor1Component;
    private Component anchor2Component;
    private Component anchor3Component;
    private ConstraintLayoutGroup currentGroup;
    private final Map<String, Component> id2ComponentMap = new HashMap<>();
    private final ConstraintLayout layout;
    private int gapBetweenComponents;


    protected ConstraintLayoutBuilder(Container container) {
        super(container);
        layout = new ConstraintLayout();
        container.setLayout(layout);
    }

    public ConstraintLayoutBuilder gapBetweenComponents(int value) {
        this.gapBetweenComponents = value;
        return this;
    }

    public ConstraintLayoutBuilder createGroup(String id) {
        ConstraintLayoutGroup group = layout.createGroup();
        if (id2ComponentMap.containsKey(id)) {
            throw new IllegalArgumentException("Component/group with id [" + id + "] is already present in builder");
        }

        currentGroup = group;
        add(id, group);
        return this;
    }

    /**
     * Add current component to current group
     */
    public ConstraintLayoutBuilder addToCurrentGroup() {
        checkCurrentComponent();
        checkCurrentGroup();
        currentGroup.addComponent(currentComponent);
        return this;
    }

    /**
     * Add component with id componentId to group with id groupId
     */
    public ConstraintLayoutBuilder addToGroup(String componentId, String groupId) {
        Component componentToAdd = getComponentById(componentId);
        Component groupComponent = getComponentById(groupId);

        if (groupComponent instanceof ConstraintLayoutGroup) {
            ConstraintLayoutGroup group = (ConstraintLayoutGroup) groupComponent;
            group.addComponent(componentToAdd);
        } else {
            throw new IllegalArgumentException("Component with id [" + groupId + "] is not group");
        }

        return this;
    }

    /**
     * Add current component to group with id groupId
     */
    public ConstraintLayoutBuilder addToGroup(String groupId) {
        checkCurrentComponent();
        Component groupComponent = getComponentById(groupId);
        if (groupComponent instanceof ConstraintLayoutGroup) {
            ConstraintLayoutGroup group = (ConstraintLayoutGroup) groupComponent;
            group.addComponent(currentComponent);
        } else {
            throw new IllegalArgumentException("Component with id [" + groupId + "] is not group");
        }

        return this;
    }


    public ConstraintLayoutBuilder globalMargin(int top, int left, int bottom, int right) {
        layout.setMargin(top, left, bottom, right);
        return this;
    }

    public ConstraintLayoutBuilder id(String id) {
        checkCurrentComponent();
        if (id2ComponentMap.containsKey(id)) {
            throw new IllegalArgumentException("Component/group with id [" + id + "] is already present in builder");
        }

        id2ComponentMap.put(id, currentComponent);
        return this;
    }

    public ConstraintLayoutBuilder anchor1() {
        checkCurrentComponent();
        anchor1Component = currentComponent;
        return this;
    }

    public ConstraintLayoutBuilder anchor2() {
        checkCurrentComponent();
        anchor2Component = currentComponent;
        return this;
    }

    public ConstraintLayoutBuilder anchor3() {
        checkCurrentComponent();
        anchor3Component = currentComponent;
        return this;
    }

    public ConstraintLayoutBuilder add(Component component) {
        add(null, component);
        return this;
    }

    public ConstraintLayoutBuilder add(String id, Component component) {
        container.add(component);
        previousComponent = currentComponent;
        currentComponent = component;
        if (id != null) {
            id(id);
        }
        return this;
    }

    public ConstraintLayoutBuilder link(Component component, Edge edge, String anchorComponentId, Edge anchorEdge, int gap) {
        layout.addConstraint(new Constraint(anchorEdge, getComponentById(anchorComponentId), gap, edge, component));
        return this;
    }

    /**
     * Attach current component to other component with id anchorComponentId
     */
    public ConstraintLayoutBuilder linkToId(Edge edge, String anchorComponentId, Edge anchorEdge) {
        checkCurrentComponent();
        return link(currentComponent, edge, anchorComponentId, anchorEdge, gapBetweenComponents);
    }

    /**
     * Attach current component to other component with id anchorComponentId
     */
    public ConstraintLayoutBuilder linkToId(Edge edge, String anchorComponentId, Edge anchorEdge, int gap) {
        checkCurrentComponent();
        return link(currentComponent, edge, anchorComponentId, anchorEdge, gap);
    }

    /**
     * Attach component with id componentId to other component with id anchorComponentId
     */
    public ConstraintLayoutBuilder linkIdToId(String componentId, Edge edge, String anchorComponentId, Edge anchorEdge) {
        Component component = getComponentById(componentId);
        return link(component, edge, anchorComponentId, anchorEdge, gapBetweenComponents);
    }

    /**
     * Attach component with id componentId to other component with id anchorComponentId
     */
    public ConstraintLayoutBuilder linkIdToId(String componentId, Edge edge, String anchorComponentId, Edge anchorEdge, int gap) {
        Component component = getComponentById(componentId);
        return link(component, edge, anchorComponentId, anchorEdge, gap);
    }

    /**
     * Attach current component to parent component
     */
    public ConstraintLayoutBuilder linkToParent(Edge edge, Edge parentEdge) {
        checkCurrentComponent();
        layout.addConstraint(new Constraint(parentEdge, container, gapBetweenComponents, edge, currentComponent));
        return this;
    }

    /**
     * Attach current component to parent component
     */
    public ConstraintLayoutBuilder linkToParent(Edge edge, Edge parentEdge, int gap) {
        checkCurrentComponent();
        layout.addConstraint(new Constraint(parentEdge, container, gap, edge, currentComponent));
        return this;
    }

    /**
     * Attach current component to parent component
     */
    public ConstraintLayoutBuilder linkToPrevious(Edge edge, Edge previousComponentEdge) {
        checkCurrentComponent();
        checkPreviousComponent();
        layout.addConstraint(new Constraint(previousComponentEdge, previousComponent, gapBetweenComponents, edge, currentComponent));
        return this;
    }

    /**
     * Attach current component to parent component
     */
    public ConstraintLayoutBuilder linkToPrevious(Edge edge, Edge previousComponentEdge, int gap) {
        checkCurrentComponent();
        checkPreviousComponent();
        layout.addConstraint(new Constraint(previousComponentEdge, previousComponent, gap, edge, currentComponent));
        return this;
    }

    /**
     * Attach current component to anchor1 component
     */
    public ConstraintLayoutBuilder linkToAnchor1(Edge edge, Edge anchor1ComponentEdge) {
        checkCurrentComponent();
        checkAnchor1Component();
        layout.addConstraint(new Constraint(anchor1ComponentEdge, anchor1Component, gapBetweenComponents, edge, currentComponent));
        return this;
    }

    /**
     * Attach current component to anchor1 component
     */
    public ConstraintLayoutBuilder linkToAnchor1(Edge edge, Edge anchor1ComponentEdge, int gap) {
        checkCurrentComponent();
        checkAnchor1Component();
        layout.addConstraint(new Constraint(anchor1ComponentEdge, anchor1Component, gap, edge, currentComponent));
        return this;
    }

    /**
     * Attach current component to anchor2 component
     */
    public ConstraintLayoutBuilder linkToAnchor2(Edge edge, Edge anchor2ComponentEdge) {
        checkCurrentComponent();
        checkAnchor2Component();
        layout.addConstraint(new Constraint(anchor2ComponentEdge, anchor2Component, gapBetweenComponents, edge, currentComponent));
        return this;
    }

    /**
     * Attach current component to anchor2 component
     */
    public ConstraintLayoutBuilder linkToAnchor2(Edge edge, Edge anchor2ComponentEdge, int gap) {
        checkCurrentComponent();
        checkAnchor2Component();
        layout.addConstraint(new Constraint(anchor2ComponentEdge, anchor2Component, gap, edge, currentComponent));
        return this;
    }

    /**
     * Attach current component to anchor3 component
     */
    public ConstraintLayoutBuilder linkToAnchor3(Edge edge, Edge anchor3ComponentEdge) {
        checkCurrentComponent();
        checkAnchor3Component();
        layout.addConstraint(new Constraint(anchor3ComponentEdge, anchor3Component, gapBetweenComponents, edge, currentComponent));
        return this;
    }

    /**
     * Attach current component to anchor3 component
     */
    public ConstraintLayoutBuilder linkToAnchor3(Edge edge, Edge anchor3ComponentEdge, int gap) {
        checkCurrentComponent();
        checkAnchor3Component();
        layout.addConstraint(new Constraint(anchor3ComponentEdge, anchor3Component, gap, edge, currentComponent));
        return this;
    }

    /**
     * Set preferred size of last added component
     */
    public ConstraintLayoutBuilder preferredSize(int width, int height) {
        currentComponent.setPreferredSize(new Dimension(width, height));
        return this;
    }

    /**
     * Set minimum size of last added component
     */
    public ConstraintLayoutBuilder minimumSize(int width, int height) {
        currentComponent.setMinimumSize(new Dimension(width, height));
        return this;
    }

    /**
     * Set maximum size of last added component
     */
    public ConstraintLayoutBuilder maximumSize(int width, int height) {
        currentComponent.setMaximumSize(new Dimension(width, height));
        return this;
    }

    private Component getComponentById(String id) {
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


}