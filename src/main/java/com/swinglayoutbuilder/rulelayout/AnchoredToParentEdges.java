package com.swinglayoutbuilder.rulelayout;

import java.awt.Rectangle;

public class AnchoredToParentEdges {
    private final Object object;
    private final boolean left;
    private final boolean top;
    private final boolean right;
    private final boolean bottom;
    private float leftMultiplier;
    private float topMultiplier;
    private float rightMultiplier;
    private float bottomMultiplier;
    private Rectangle capturedBounds;

    public AnchoredToParentEdges(Object object, boolean left, boolean top, boolean right, boolean bottom) {
        this.object = object;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public AnchoredToParentEdges setMultipliers(float leftMultiplier, float topMultiplier, float rightMultiplier, float bottomMultiplier) {
        this.leftMultiplier = leftMultiplier;
        this.topMultiplier = topMultiplier;
        this.rightMultiplier = rightMultiplier;
        this.bottomMultiplier = bottomMultiplier;
        return this;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isTop() {
        return top;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isBottom() {
        return bottom;
    }

    public float getLeftMultiplier() {
        return leftMultiplier;
    }

    public float getTopMultiplier() {
        return topMultiplier;
    }

    public float getRightMultiplier() {
        return rightMultiplier;
    }

    public float getBottomMultiplier() {
        return bottomMultiplier;
    }

    public Object getObject() {
        return object;
    }

    public Rectangle getCapturedBounds() {
        return capturedBounds;
    }

    public void setCapturedBounds(Rectangle capturedBounds) {
        this.capturedBounds = capturedBounds;
    }
}
