package com.swinglayoutbuilder.rulelayout;

import java.awt.Dimension;
import java.awt.Rectangle;

public interface ComponentActionsWrapper {
    Rectangle getComponentRect(Object component);

    int getBaseLine(Object component, int width, int height);

    void setComponentRect(Object component, Rectangle rect);

    Object[] getChildren(Object component);

    Dimension getMinimumSize(Object component);

    Dimension getPreferredSize(Object component);

    void setPreferredSize(Object component, Dimension dimension);

    void setMinimumSize(Object component, Dimension dimension);
}
