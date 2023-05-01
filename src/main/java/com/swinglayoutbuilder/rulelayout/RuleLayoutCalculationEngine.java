package com.swinglayoutbuilder.rulelayout;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.*;

public class RuleLayoutCalculationEngine {
    private final Insets layoutPadding = new Insets(0, 0, 0, 0);
    private final List<Rule> rules = new ArrayList<>();
    private final Map<Object, ComponentRect> componentRectangleMap = new HashMap<>();
    private final Set<LayoutGroup> groups = new HashSet<>();
    private final ComponentActionsWrapper componentActionsWrapper;
    private final Object parent;
    private int defaultWidth = -1;
    private int defaultHeight = -1;
    private final List<AnchoredToParentEdges> componentsAnchoredToParentEdges = new ArrayList<>();

    public RuleLayoutCalculationEngine(Object parent, ComponentActionsWrapper componentActionsWrapper) {
        this.parent = parent;
        this.componentActionsWrapper = componentActionsWrapper;
    }

    public Object getParent() {
        return parent;
    }

    public LayoutGroup createGroup() {
        LayoutGroup newGroup = new LayoutGroup(this);
        groups.add(newGroup);
        return newGroup;
    }

    public void setComponentAnchoredToParentEdges(AnchoredToParentEdges anchoredToParentEdges) {
        componentsAnchoredToParentEdges.add(anchoredToParentEdges);
    }

    public void addRule(Rule... rules) {
        Collections.addAll(this.rules, rules);
    }

    public void setPadding(int left, int top, int right, int bottom) {
        layoutPadding.set(top, left, bottom, right);
    }

    public void invalidate() {
        componentRectangleMap.clear();
    }

    public Dimension getPreferredSize() {
        runCalculations(parent, SizeType.PREF);
        return calculateBounds(parent);
    }

    public Dimension getMinimumSize() {
        runCalculations(parent, SizeType.MIN);
        return calculateBounds(parent);
    }

    public void layoutContainer() {
        boolean firstLayout = false;
        if (defaultWidth == -1 || defaultHeight == -1) {
            Rectangle rectangle = componentActionsWrapper.getComponentRect(parent);
            defaultWidth = rectangle.width;
            defaultHeight = rectangle.height;
            firstLayout = true;
        }
        runCalculations(parent, SizeType.PREF);

        Map<Object, Rectangle> realBounds = new HashMap<>();
        for (Object component : componentActionsWrapper.getChildren(parent)) {
            ComponentRect rect = getRect(component);
            realBounds.put(component, new Rectangle(rect.getX() + layoutPadding.left, rect.getY() + layoutPadding.top, rect.getWidth(), rect.getHeight()));
        }

        //capture initial positions of components anchored to parent edges after first layout
        if (firstLayout) {
            for (AnchoredToParentEdges anchor : componentsAnchoredToParentEdges) {

                anchor.setCapturedBounds(componentActionsWrapper.getComponentRect(anchor.getObject()));
            }
        } else {
            processParentResize(realBounds);
        }

        //appy rectangle to real components
        for (Object component : componentActionsWrapper.getChildren(parent)) {
            componentActionsWrapper.setComponentRect(component, realBounds.get(component));
        }
    }

    private void processParentResize(Map<Object, Rectangle> realBounds) {
        Rectangle rectangle = componentActionsWrapper.getComponentRect(parent);
        if (defaultWidth == rectangle.width && defaultHeight == rectangle.height) return;
        int dw = rectangle.width - defaultWidth;
        int dh = rectangle.height - defaultHeight;
        for (AnchoredToParentEdges anchor : componentsAnchoredToParentEdges) {
            //Rectangle capturedBounds = anchor.getCapturedBounds();
            Rectangle newBounds = realBounds.get(unwrapComponentWrapper(anchor.getObject()));
            int newX = newBounds.x;
            int newY = newBounds.y;
            int newX2 = newBounds.x + newBounds.width;
            int newY2 = newBounds.y + newBounds.height;
            //if edge bound to parent LEFT OR TOP edge - nothing will change, because they does not move
            if (anchor.isLeft()) {
                newX += dw * anchor.getLeftMultiplier();
            }
            if (anchor.isTop()) {
                newY += dh * anchor.getTopMultiplier();
            }
            if (anchor.isRight()) {
                newX2 += dw * anchor.getRightMultiplier();
            }
            if (anchor.isBottom()) {
                newY2 += dh * anchor.getBottomMultiplier();
            }
            newBounds.setBounds(newX, newY, newX2 - newX, newY2 - newY);
        }
    }

    private Dimension calculateBounds(Object parent) {
        int maxX = 0;
        int maxY = 0;
        for (Object component : componentActionsWrapper.getChildren(parent)) {
            ComponentRect rect = getRect(component);
            maxX = Math.max(maxX, rect.getX2());
            maxY = Math.max(maxY, rect.getY2());
        }

        if (componentRectangleMap.containsKey(parent)) {
            ComponentRect rect = getRect(parent);
            maxX = Math.max(maxX, rect.getX2());
            maxY = Math.max(maxY, rect.getY2());
        }

        maxX += layoutPadding.left + layoutPadding.right;
        maxY += layoutPadding.top + layoutPadding.bottom;
        return new Dimension(maxX, maxY);
    }


    private void runCalculations(Object parent, SizeType sizeType) {
        for (Object component : componentActionsWrapper.getChildren(parent)) {
            ComponentRect rect = getRect(component);
            Dimension sizeDimension;
            if (sizeType == SizeType.MIN) {
                sizeDimension = componentActionsWrapper.getMinimumSize(component);
            } else if (sizeType == SizeType.PREF) {
                sizeDimension = componentActionsWrapper.getPreferredSize(component);
            } else {
                throw new RuntimeException("Size type is not implemented " + sizeType);
            }

            rect.reset(0, 0, sizeDimension.width, sizeDimension.height);
        }

        ComponentRect parentComponentRect = getRect(parent);
        Rectangle realParentRect = componentActionsWrapper.getComponentRect(parent);
        parentComponentRect.reset(0, 0, (int) realParentRect.getWidth() - layoutPadding.left - layoutPadding.right, (int) realParentRect.getHeight() - layoutPadding.top - layoutPadding.bottom);
        parentComponentRect.fixX1Y1Position();
        for (Rule rule : rules) {
            ComponentRect anchorComponentRect = getRect(rule.getAnchorComponent());
            ComponentRect componentRect = getRect(rule.getComponent());
            if (rule.getComponent() instanceof LayoutGroup) {
                int position = getPosition(anchorComponentRect, rule.getAnchorComponent(), rule.getAnchorEdge());
                setPosition(componentRect, rule.getComponent(), rule.getEdge(), position + rule.getOffset());
            } else {
                int position = getPosition(anchorComponentRect, rule.getAnchorComponent(), rule.getAnchorEdge());
                setPosition(componentRect, rule.getComponent(), rule.getEdge(), position + rule.getOffset());
            }
        }
    }

    private Object unwrapComponentWrapper(Object potentialComponentWrapper) {
        if (potentialComponentWrapper instanceof ComponentWrapper) {
            ComponentWrapper wraper = (ComponentWrapper) potentialComponentWrapper;
            if (wraper.isGroup()) {
                return wraper.getGroup();
            } else {
                return wraper.getComponent();
            }
        }
        return potentialComponentWrapper;
    }

    ComponentRect getRect(Object component) {
        component = unwrapComponentWrapper(component);
        if (component instanceof LayoutGroup && groups.contains(component)) {
            LayoutGroup group = (LayoutGroup) component;
            return group.getRect();
        }
        ComponentRect rect = componentRectangleMap.get(component);
        if (rect == null) {
            rect = new ComponentRect();
            componentRectangleMap.put(component, rect);
        }
        return rect;
    }

    private int getPosition(ComponentRect rect, Object component, Edge edge) {
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
                return componentActionsWrapper.getBaseLine(component, rect.getWidth(), rect.getHeight()) + rect.getY();
            default:
                throw new RuntimeException("Unknown edge " + edge);
        }
    }

    private void setPosition(ComponentRect rect, Object component, Edge edge, int value) {
        component = unwrapComponentWrapper(component);
        if (component instanceof LayoutGroup) {
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


            ((LayoutGroup) component).moveChildren(diffX, diffY);
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
                    int baseLine = componentActionsWrapper.getBaseLine(component, rect.getWidth(), rect.getHeight()) + rect.getY();
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
        MIN, PREF
    }
}
