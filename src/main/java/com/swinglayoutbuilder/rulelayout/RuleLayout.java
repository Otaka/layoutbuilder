package com.swinglayoutbuilder.rulelayout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.function.Function;

@SuppressWarnings("UnusedReturnValue")
public class RuleLayout implements LayoutManager2 {

    private final RuleLayoutCalculationEngine engine;
    private final ComponentActionsWrapper componentActionsWrapper;

    public RuleLayout(Container parent) {
        componentActionsWrapper = new ComponentActionsWrapper() {
            private Object map(Object component, Function<LayoutGroup<Component>, Object> groupMapping, Function<Component, Object> componentMapper) {
                if (component instanceof ComponentWrapper) {
                    ComponentWrapper<Component> wrapper = (ComponentWrapper<Component>) component;
                    if (wrapper.isGroup()) {
                        return groupMapping.apply(wrapper.getGroup());
                    } else {
                        return componentMapper.apply(wrapper.getComponent());
                    }
                } else {
                    return componentMapper.apply((Component) component);
                }
            }

            @Override
            public Rectangle getComponentRect(Object component) {
                return (Rectangle) map(component, LayoutGroup::getRect, Component::getBounds);
            }

            @Override
            public int getBaseLine(Object component, int width, int height) {
                return (int) map(component, (g) -> {
                    throw new IllegalArgumentException("Cannot get base line from group");
                }, (c) -> c.getBaseline(width, height));
            }

            @Override
            public void setComponentRect(Object component, Rectangle rect) {
                map(component, (g) -> null, (c) -> {
                    c.setBounds(rect);
                    return null;
                });
            }

            @Override
            public Object[] getChildren(Object component) {
                return (Object[]) map(component, LayoutGroup::getChildren, (c) -> ((Container) c).getComponents());
            }

            @Override
            public Dimension getMinimumSize(Object component) {
                return (Dimension) map(component, LayoutGroup::getRect, Component::getMinimumSize);
            }

            @Override
            public Dimension getPreferredSize(Object component) {
                return (Dimension) map(component, LayoutGroup::getRect, Component::getPreferredSize);
            }

            @Override
            public void setPreferredSize(Object component, Dimension dimension) {
                map(component, (g) -> null, (c) -> {
                    c.setPreferredSize(dimension);
                    return null;
                });
            }

            @Override
            public void setMinimumSize(Object component, Dimension dimension) {
                map(component, (g) -> null, (c) -> {
                    c.setMinimumSize(dimension);
                    return null;
                });
            }
        };
        engine = new RuleLayoutCalculationEngine(parent, componentActionsWrapper);
    }

    public LayoutGroup<Component> createGroup() {
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

    public void setPreferredSize(Object component, Dimension dimension) {
        componentActionsWrapper.setPreferredSize(component, dimension);
    }

    public void setMinimumSize(Object component, Dimension dimension) {
        componentActionsWrapper.setMinimumSize(component, dimension);
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