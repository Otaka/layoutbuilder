package com.swinglayoutbuilder.constraintlayout;

import java.awt.*;
import java.util.List;
import java.util.*;

public class ConstraintLayout implements LayoutManager2 {
    private final List<Constraint> rules = new ArrayList<>();
    private final Map<Component, ComponentRect> componentRectangleMap = new HashMap<>();
    private final Set<ConstraintLayoutGroup> groups = new HashSet<>();
    private final Insets layoutMargin = new Insets(0, 0, 0, 0);
    private final Container parent;

    public ConstraintLayout(Container parent) {
        this.parent = parent;
    }

    public ConstraintLayoutGroup createGroup() {
        ConstraintLayoutGroup newGroup = new ConstraintLayoutGroup(this);
        groups.add(newGroup);
        return newGroup;
    }

    public ConstraintLayout addRule(Constraint... rules) {
        for (Constraint c : rules) {
            this.rules.add(c);
        }
        return this;
    }

    public ConstraintLayout setMargin(int top, int left, int bottom, int right) {
        layoutMargin.set(top, left, bottom, right);
        return this;
    }

    @Override
    public void addLayoutComponent(Component comp, Object rule) {
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
        componentRectangleMap.clear();
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension maximumLayoutSize(Container parent) {
        checkParent(parent);
        runCalculations(parent, SizeType.MAX);
        return calculateBounds(parent);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        checkParent(parent);
        runCalculations(parent, SizeType.PREF);
        return calculateBounds(parent);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        checkParent(parent);
        runCalculations(parent, SizeType.MIN);
        return calculateBounds(parent);
    }

    private Dimension calculateBounds(Container parent) {
        int maxX = 0;
        int maxY = 0;
        for (Component component : parent.getComponents()) {
            ComponentRect rect = getRect(component);
            maxX = Math.max(maxX, rect.getX2());
            maxY = Math.max(maxY, rect.getY2());
        }

        if (componentRectangleMap.containsKey(parent)) {
            ComponentRect rect = getRect(parent);
            maxX = Math.max(maxX, rect.getX2());
            maxY = Math.max(maxY, rect.getY2());
        }

        maxX += layoutMargin.left + layoutMargin.right;
        maxY += layoutMargin.top + layoutMargin.bottom;
        return new Dimension(maxX, maxY);
    }

    @Override
    public void layoutContainer(Container parent) {
        checkParent(parent);
        runCalculations(parent, SizeType.PREF);

        for (Component component : parent.getComponents()) {
            ComponentRect rect = getRect(component);
            component.setBounds(rect.getX() + layoutMargin.left, rect.getY() + layoutMargin.top, rect.getWidth(), rect.getHeight());
        }
    }

    private void runCalculations(Container parent, SizeType sizeType) {
        for (Component component : parent.getComponents()) {
            ComponentRect rect = getRect(component);
            Dimension sizeDimension;
            if (sizeType == SizeType.MIN) {
                sizeDimension = component.getMinimumSize();
            } else if (sizeType == SizeType.MAX) {
                sizeDimension = component.getMaximumSize();
            } else if (sizeType == SizeType.PREF) {
                sizeDimension = component.getPreferredSize();
            } else {
                throw new RuntimeException("Size type is not implemented " + sizeType);
            }

            rect.reset(0, 0, sizeDimension.width, sizeDimension.height);
        }

        ComponentRect parentComponentRect = getRect(parent);
        parentComponentRect.reset(0, 0, parent.getWidth() - layoutMargin.left - layoutMargin.right, parent.getHeight() - layoutMargin.top - layoutMargin.bottom);
        parentComponentRect.fixX1Y1Position();
        for (Constraint rule : rules) {
            ComponentRect anchorComponentRect = getRect(rule.getAnchorComponent());
            ComponentRect componentRect = getRect(rule.getComponent());
            if (rule.getComponent() instanceof ConstraintLayoutGroup) {
                int position = getPosition(anchorComponentRect, rule.getAnchorComponent(), rule.getAnchorEdge());
                setPosition(componentRect, rule.getComponent(), rule.getEdge(), position + rule.getOffset());
            } else {
                int position = getPosition(anchorComponentRect, rule.getAnchorComponent(), rule.getAnchorEdge());
                setPosition(componentRect, rule.getComponent(), rule.getEdge(), position + rule.getOffset());
            }
        }
    }

    private void checkParent(Container container) {
        if (container != parent) {
            throw new IllegalStateException("One instance of RuleLayout cannot be assigned to several containers");
        }
    }

    ComponentRect getRect(Component component) {
        if (component instanceof ConstraintLayoutGroup && groups.contains(component)) {
            ConstraintLayoutGroup group = (ConstraintLayoutGroup) component;
            return group.getRect();
        }
        ComponentRect rect = componentRectangleMap.get(component);
        if (rect == null) {
            rect = new ComponentRect();
            componentRectangleMap.put(component, rect);
        }
        return rect;
    }

    private int getPosition(ComponentRect rect, Component component, Edge edge) {
        switch (edge) {
            case LEFT:
                return rect.getX();
            case RIGHT:
                return rect.getX2();
            case TOP:
                return rect.getY();
            case BOTTOM:
                return rect.getY2();
            case WIDTH:
                return rect.getWidth();
            case HEIGHT:
                return rect.getHeight();
            case HOR_CENTER:
                return rect.getX() + rect.getWidth() / 2;
            case VER_CENTER:
                return rect.getY() + rect.getHeight() / 2;
            case BASELINE:
                return component.getBaseline(rect.getWidth(), rect.getHeight()) + rect.getY();
            default:
                throw new RuntimeException("Unknown edge " + edge);
        }
    }

    private void setPosition(ComponentRect rect, Component component, Edge edge, int value) {
        if (component instanceof ConstraintLayoutGroup) {
            int diffX = 0;
            int diffY = 0;
            switch (edge) {
                case LEFT: {
                    diffX = value - rect.getX();
                    break;
                }
                case TOP: {
                    diffY = value - rect.getY();
                    break;
                }
                case RIGHT: {
                    diffX = value - rect.getX2();
                    break;
                }
                case BOTTOM: {
                    diffY = value - rect.getY2();
                    break;
                }
                case HOR_CENTER: {
                    diffX = value - (rect.getX() + ((rect.getX2() - rect.getX()) / 2));
                    break;
                }
                case VER_CENTER: {
                    diffY = value - (rect.getY() + ((rect.getY2() - rect.getY()) / 2));
                    break;
                }
            }


            ((ConstraintLayoutGroup) component).moveChildren(diffX, diffY);
        } else {
            switch (edge) {
                case LEFT: {
                    rect.setX(value);
                    break;
                }
                case RIGHT: {
                    rect.setX2(value);
                    break;
                }
                case TOP: {
                    rect.setY(value);
                    break;
                }
                case BOTTOM: {
                    rect.setY2(value);
                    break;
                }
                case WIDTH: {
                    rect.setWidth(value);
                    break;
                }
                case HEIGHT: {
                    rect.setHeight(value);
                    break;
                }
                case HOR_CENTER: {
                    rect.moveX(value - rect.getWidth() / 2);
                    break;
                }
                case VER_CENTER: {
                    rect.moveY(value - rect.getHeight() / 2);
                    break;
                }
                case BASELINE: {
                    int baseLine = component.getBaseline(rect.getWidth(), rect.getHeight()) + rect.getY();
                    int diff = value - baseLine;
                    rect.moveY(rect.getY() + diff);
                    break;
                }
                default:
                    throw new RuntimeException("Unknown edge " + edge);
            }
        }
    }

    private enum SizeType {
        MIN, MAX, PREF
    }
}