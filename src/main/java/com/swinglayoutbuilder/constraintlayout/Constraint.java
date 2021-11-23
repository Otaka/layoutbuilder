package com.swinglayoutbuilder.constraintlayout;

import javax.swing.*;
import java.awt.Component;

public class Constraint {
    private final Edge anchorEdge;
    private final Component anchorComponent;
    private final Edge edge;
    private Component component;
    private final int offset;


    public Constraint(Edge anchorEdge, Component anchorComponent, int offset, Edge edge, Component component) {
        this.anchorEdge = anchorEdge;
        this.anchorComponent = anchorComponent;
        this.edge = edge;
        this.component = component;
        this.offset = offset;
    }

    public void setComponent(JComponent component) {
        this.component = component;
    }

    public int getOffset() {
        return offset;
    }

    public Edge getAnchorEdge() {
        return anchorEdge;
    }

    public Component getAnchorComponent() {
        return anchorComponent;
    }

    public Edge getEdge() {
        return edge;
    }

    public Component getComponent() {
        return component;
    }

}