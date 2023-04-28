package com.swinglayoutbuilder.rulelayout;

import java.awt.*;

public interface ComponentActionsWrapper {
    Rectangle getComponentRect(Object component);

    int getBaseLine(Object component, int width, int height);

    void setComponentRect(Object component, Rectangle rect);

    Object[] getChildren(Object component);

    Dimension getMinimumSize(Object component);

    Dimension getPreferredSize(Object component);
}
