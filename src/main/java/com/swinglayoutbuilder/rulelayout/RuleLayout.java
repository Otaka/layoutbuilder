package com.swinglayoutbuilder.rulelayout;

import java.awt.*;

public class RuleLayout implements LayoutManager2 {

    private final RuleLayoutCalculationEngine engine;

    public RuleLayout(Container parent) {
        engine = new RuleLayoutCalculationEngine(parent, new ComponentActionsWrapper() {
            @Override
            public Rectangle getComponentRect(Object component) {
                return ((Component) component).getBounds();
            }

            @Override
            public int getBaseLine(Object component, int width, int height) {
                return ((Component) component).getBaseline(width, height);
            }

            @Override
            public void setComponentRect(Object component, Rectangle rect) {
                ((Component) component).setBounds(rect);
            }

            @Override
            public Object[] getChildren(Object component) {
                return ((Container) component).getComponents();
            }

            @Override
            public Dimension getMinimumSize(Object component) {
                return ((Component) component).getMinimumSize();
            }

            @Override
            public Dimension getPreferredSize(Object component) {
                return ((Component) component).getPreferredSize();
            }
        });
    }

    public LayoutGroup createGroup() {
        return engine.createGroup();
    }

    public RuleLayout addRule(Rule... rules) {
        engine.addRule(rules);
        return this;
    }

    public RuleLayout setPadding(int left, int top, int right, int bottom) {
        engine.setPadding(left, top, right, bottom);
        return this;
    }

    public RuleLayout setComponentEdgeAnchoredToParentMovingEdge(Object component,
                                                                 boolean left, boolean top, boolean right, boolean bottom,
                                                                 float leftMultiplier, float topMultiplier, float rightMultiplier, float bottomMultiplier) {
        engine.setComponentAnchoredToParentEdges(new AnchoredToParentEdges(component, left, top, right, bottom)
                .setMultipliers(leftMultiplier, topMultiplier, rightMultiplier, bottomMultiplier));
        return this;
    }

    @Override
    public void addLayoutComponent(Component comp, Object rule) {
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {
        engine.invalidate();
    }

    @Override
    public Dimension maximumLayoutSize(Container parent) {
        checkParent(parent);
        return engine.getPreferredSize();
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        checkParent(parent);
        return engine.getPreferredSize();
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        checkParent(parent);
        return engine.getMinimumSize();
    }

    @Override
    public void layoutContainer(Container parent) {
        checkParent(parent);
        engine.layoutContainer();
    }

    private void checkParent(Container container) {
        if (container != engine.getParent()) {
            throw new IllegalStateException("One instance of RuleLayout cannot be assigned to several containers");
        }
    }
}