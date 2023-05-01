package com.swinglayoutbuilder.rulelayout;

public class ComponentWrapper<T> {
    private T component;
    private LayoutGroup<T> group;

    public boolean isGroup() {
        return group != null;
    }

    public ComponentWrapper(T component) {
        this.component = component;
    }

    public ComponentWrapper(LayoutGroup<T> group) {
        this.group = group;
    }

    public LayoutGroup<T> getGroup() {
        return group;
    }

    public T getComponent() {
        return component;
    }
}
