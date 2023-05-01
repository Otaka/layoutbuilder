package com.swinglayoutbuilder.rulelayout;

import javax.swing.*;

public class Rule {
    private final Edge anchorEdge;
    private final Object anchorComponent;
    private final Edge edge;
    private final Object component;
    private final int offset;


    public Rule(Edge anchorEdge, Object anchorComponent, int offset, Edge edge, Object component) {
        this.anchorEdge = anchorEdge;
        this.anchorComponent = anchorComponent;
        this.edge = edge;
        this.component = component;
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public Edge getAnchorEdge() {
        return anchorEdge;
    }

    public Object getAnchorComponent() {
        return anchorComponent;
    }

    public Edge getEdge() {
        return edge;
    }

    public Object getComponent() {
        return component;
    }
}