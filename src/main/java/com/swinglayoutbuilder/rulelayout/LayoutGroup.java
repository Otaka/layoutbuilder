package com.swinglayoutbuilder.rulelayout;

import java.util.ArrayList;
import java.util.List;

public class LayoutGroup {
    private final List<Object> children = new ArrayList<>();
    private final ComponentRect rectangle = new ComponentRect();
    private final RuleLayoutCalculationEngine owner;

    public LayoutGroup(RuleLayoutCalculationEngine owner) {
        this.owner = owner;
    }

    public void addComponent(Object component) {
        children.add(component);
    }

    public ComponentRect getRect() {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (Object component : children) {
            ComponentRect rect = owner.getRect(component);
            if (rect.getX() < minX) {
                minX = rect.getX();
            }
            if (rect.getY() < minY) {
                minY = rect.getY();
            }
            if (rect.getX2() > maxX) {
                maxX = rect.getX2();
            }
            if (rect.getY2() > maxY) {
                maxY = rect.getY2();
            }
        }

        rectangle.reset(minX, minY, maxX - minX, maxY - minY);
        return rectangle;
    }

    public void moveChildren(int x, int y) {
        for (Object component : children) {
            ComponentRect rect = owner.getRect(component);
            rect.moveX(rect.getX() + x);
            rect.moveY(rect.getY() + y);
        }
    }

    public List<Object> getChildren() {
        return children;
    }
}