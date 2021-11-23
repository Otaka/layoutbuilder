package com.swinglayoutbuilder.constraintlayout;

public class ComponentRect {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean x1Set = false;
    private boolean y1Set = false;
    private boolean x2Set = false;
    private boolean y2Set = false;

    public ComponentRect() {
    }

    public void reset(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        x2Set = false;
        y2Set = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void moveX(int value) {
        this.x = value;
    }

    public void moveY(int value) {
        this.y = value;
    }

    public void setX(int x) {
        if (x2Set) {
            int lastX2 = getX2();
            width = lastX2 - x;
        }

        this.x = x;
        x1Set = true;
        normalize();
    }

    public void setY(int y) {
        if (y2Set) {
            int lastY2 = getY2();
            height = lastY2 - y;
        }

        this.y = y;
        y1Set = true;
        normalize();
    }

    public void setWidth(int width) {
        if (x2Set) {
            int lastX2 = getX2();
            x = lastX2 - width;
        }
        this.width = width;
    }

    public void setHeight(int height) {
        if (y2Set) {
            int lastY2 = getY2();
            y = lastY2 - height;
        }
        this.height = height;
    }

    public int getX2() {
        return x + width;
    }

    public int getY2() {
        return y + height;
    }

    public void setX2(int value) {
        if (x1Set) {
            width = value - x;
        } else {
            x = value - width;
        }
        x2Set = true;
    }

    public void setY2(int value) {
        if (y1Set) {
            height = value - y;
        } else {
            y = value - height;
        }
        y2Set = true;
    }

    public void normalize() {
        if (width < 0) {
            width = -width;
            x = x - width;
        }
        if (height < 0) {
            height = -height;
            y = y - height;
        }
    }

}