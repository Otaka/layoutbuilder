package com.swinglayoutbuilder;

import com.swinglayoutbuilder.rulelayout.Rule;
import com.swinglayoutbuilder.rulelayout.RuleLayout;
import com.swinglayoutbuilder.rulelayout.RuleLayoutGroup;
import com.swinglayoutbuilder.rulelayout.Edge;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RuleLayoutBuilder extends AbstractBuilder<RuleLayoutBuilder> {

    private Component previousComponent;
    private Component currentComponent;
    private Component anchor1Component;
    private Component anchor2Component;
    private Component anchor3Component;
    private RuleLayoutGroup currentGroup;
    private final Map<String, Component> id2ComponentMap = new HashMap<>();
    private final RuleLayout layout;
    private int gapBetweenComponents;


    protected RuleLayoutBuilder(Container container) {
        super(container);
        layout = new RuleLayout(container);
        container.setLayout(layout);
        currentComponent=container;
    }

    public RuleLayoutBuilder gapBetweenComponents(int value) {
        this.gapBetweenComponents = value;
        return this;
    }

    public RuleLayoutBuilder createGroup(String id) {
        RuleLayoutGroup group = layout.createGroup();
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
    public RuleLayoutBuilder addToCurrentGroup() {
        checkCurrentComponent();
        checkCurrentGroup();
        currentGroup.addComponent(currentComponent);
        return this;
    }

    /**
     * Add component with id componentId to group with id groupId
     */
    public RuleLayoutBuilder addToGroup(String componentId, String groupId) {
        Component componentToAdd = getComponentById(componentId);
        Component groupComponent = getComponentById(groupId);

        if (groupComponent instanceof RuleLayoutGroup) {
            RuleLayoutGroup group = (RuleLayoutGroup) groupComponent;
            group.addComponent(componentToAdd);
        } else {
            throw new IllegalArgumentException("Component with id [" + groupId + "] is not group");
        }

        return this;
    }

    /**
     * Add current component to group with id groupId
     */
    public RuleLayoutBuilder addToGroup(String groupId) {
        checkCurrentComponent();
        Component groupComponent = getComponentById(groupId);
        if (groupComponent instanceof RuleLayoutGroup) {
            RuleLayoutGroup group = (RuleLayoutGroup) groupComponent;
            group.addComponent(currentComponent);
        } else {
            throw new IllegalArgumentException("Component with id [" + groupId + "] is not group");
        }

        return this;
    }


    public RuleLayoutBuilder globalMargin(int top, int left, int bottom, int right) {
        layout.setMargin(top, left, bottom, right);
        return this;
    }

    public RuleLayoutBuilder id(String id) {
        checkCurrentComponent();
        if (id2ComponentMap.containsKey(id)) {
            throw new IllegalArgumentException("Component/group with id [" + id + "] is already present in builder");
        }

        id2ComponentMap.put(id, currentComponent);
        return this;
    }

    public RuleLayoutBuilder anchor1() {
        checkCurrentComponent();
        anchor1Component = currentComponent;
        return this;
    }

    public RuleLayoutBuilder anchor2() {
        checkCurrentComponent();
        anchor2Component = currentComponent;
        return this;
    }

    public RuleLayoutBuilder anchor3() {
        checkCurrentComponent();
        anchor3Component = currentComponent;
        return this;
    }

    public RuleLayoutBuilder add(Component component) {
        add(null, component);
        return this;
    }

    public RuleLayoutBuilder add(String id, Component component) {
        add(container, component, null);
        previousComponent = currentComponent;
        currentComponent = component;
        if (id != null) {
            id(id);
        }
        return this;
    }

    public RuleLayoutBuilder move(Component component, Edge edge, String anchorComponentId, Edge anchorEdge, int gap) {
        layout.addRule(new Rule(anchorEdge, getComponentById(anchorComponentId), gap, edge, component));
        return this;
    }

    /**
     * Move parent edge to some other component edge by id
     */
    public RuleLayoutBuilder moveParentToId(Edge edge, String anchorComponentId, Edge anchorEdge) {
        return move(container, edge, anchorComponentId, anchorEdge, gapBetweenComponents);
    }

    /**
     * Move parent edge to other component edge by id
     */
    public RuleLayoutBuilder moveParentToId(Edge edge, String anchorComponentId, Edge anchorEdge, int gap) {
        return move(container, edge, anchorComponentId, anchorEdge, gap);
    }


    /**
     * Move parent edge to current component edge
     */
    public RuleLayoutBuilder moveParentToCurrent(Edge edge, Edge previousComponentEdge) {
        checkCurrentComponent();
        layout.addRule(new Rule(previousComponentEdge, currentComponent, gapBetweenComponents, edge, container));
        return this;
    }

    /**
     * Move parent edge to current component edge
     */
    public RuleLayoutBuilder moveParentToCurrent(Edge edge, Edge previousComponentEdge, int gap) {
        checkCurrentComponent();
        layout.addRule(new Rule(previousComponentEdge, currentComponent, gap, edge, container));
        return this;
    }

    /**
     * Move current component to other component with id anchorComponentId
     */
    public RuleLayoutBuilder moveToId(Edge edge, String anchorComponentId, Edge anchorEdge) {
        checkCurrentComponent();
        return move(currentComponent, edge, anchorComponentId, anchorEdge, gapBetweenComponents);
    }

    /**
     * Move current component to other component with id anchorComponentId
     */
    public RuleLayoutBuilder moveToId(Edge edge, String anchorComponentId, Edge anchorEdge, int gap) {
        checkCurrentComponent();
        return move(currentComponent, edge, anchorComponentId, anchorEdge, gap);
    }

    /**
     * Move component with id componentId to other component with id anchorComponentId
     */
    public RuleLayoutBuilder moveIdToId(String componentId, Edge edge, String anchorComponentId, Edge anchorEdge) {
        Component component = getComponentById(componentId);
        return move(component, edge, anchorComponentId, anchorEdge, gapBetweenComponents);
    }

    /**
     * Move component with id componentId to other component with id anchorComponentId
     */
    public RuleLayoutBuilder moveIdToId(String componentId, Edge edge, String anchorComponentId, Edge anchorEdge, int gap) {
        Component component = getComponentById(componentId);
        return move(component, edge, anchorComponentId, anchorEdge, gap);
    }

    /**
     * Move current component to parent component
     */
    public RuleLayoutBuilder moveToParent(Edge edge, Edge parentEdge) {
        checkCurrentComponent();
        layout.addRule(new Rule(parentEdge, container, gapBetweenComponents, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to parent component
     */
    public RuleLayoutBuilder moveToParent(Edge edge, Edge parentEdge, int gap) {
        checkCurrentComponent();
        layout.addRule(new Rule(parentEdge, container, gap, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to parent component
     */
    public RuleLayoutBuilder moveIdToParent(String componentId, Edge edge, Edge parentEdge, int gap) {
        Component component = getComponentById(componentId);
        layout.addRule(new Rule(parentEdge, container, gap, edge, component));
        return this;
    }

    /**
     * Move component with id componentId to parent component
     */
    public RuleLayoutBuilder moveIdToParent(String componentId, Edge edge, Edge parentEdge) {
        Component component = getComponentById(componentId);
        layout.addRule(new Rule(parentEdge, container, gapBetweenComponents, edge, component));
        return this;
    }

    /**
     * Move current component to previous component
     */
    public RuleLayoutBuilder moveToPrevious(Edge edge, Edge previousComponentEdge) {
        checkCurrentComponent();
        checkPreviousComponent();
        layout.addRule(new Rule(previousComponentEdge, previousComponent, gapBetweenComponents, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to previous component
     */
    public RuleLayoutBuilder moveToPrevious(Edge edge, Edge previousComponentEdge, int gap) {
        checkCurrentComponent();
        checkPreviousComponent();
        layout.addRule(new Rule(previousComponentEdge, previousComponent, gap, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to anchor1 component
     */
    public RuleLayoutBuilder moveToAnchor1(Edge edge, Edge anchor1ComponentEdge) {
        checkCurrentComponent();
        checkAnchor1Component();
        layout.addRule(new Rule(anchor1ComponentEdge, anchor1Component, gapBetweenComponents, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to anchor1 component
     */
    public RuleLayoutBuilder moveToAnchor1(Edge edge, Edge anchor1ComponentEdge, int gap) {
        checkCurrentComponent();
        checkAnchor1Component();
        layout.addRule(new Rule(anchor1ComponentEdge, anchor1Component, gap, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to anchor2 component
     */
    public RuleLayoutBuilder moveToAnchor2(Edge edge, Edge anchor2ComponentEdge) {
        checkCurrentComponent();
        checkAnchor2Component();
        layout.addRule(new Rule(anchor2ComponentEdge, anchor2Component, gapBetweenComponents, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to anchor2 component
     */
    public RuleLayoutBuilder moveToAnchor2(Edge edge, Edge anchor2ComponentEdge, int gap) {
        checkCurrentComponent();
        checkAnchor2Component();
        layout.addRule(new Rule(anchor2ComponentEdge, anchor2Component, gap, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to anchor3 component
     */
    public RuleLayoutBuilder moveToAnchor3(Edge edge, Edge anchor3ComponentEdge) {
        checkCurrentComponent();
        checkAnchor3Component();
        layout.addRule(new Rule(anchor3ComponentEdge, anchor3Component, gapBetweenComponents, edge, currentComponent));
        return this;
    }

    /**
     * Move current component to anchor3 component
     */
    public RuleLayoutBuilder moveToAnchor3(Edge edge, Edge anchor3ComponentEdge, int gap) {
        checkCurrentComponent();
        checkAnchor3Component();
        layout.addRule(new Rule(anchor3ComponentEdge, anchor3Component, gap, edge, currentComponent));
        return this;
    }

    /**
     * Set preferred size of last added component, or for parent in case if no components are added yet
     */
    public RuleLayoutBuilder preferredSize(int width, int height) {
        currentComponent.setPreferredSize(new Dimension(width, height));
        return this;
    }

    /**
     * Set minimum size of last added component
     */
    public RuleLayoutBuilder minimumSize(int width, int height) {
        currentComponent.setMinimumSize(new Dimension(width, height));
        return this;
    }

    /**
     * Set maximum size of last added component
     */
    public RuleLayoutBuilder maximumSize(int width, int height) {
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