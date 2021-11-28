package com.swinglayoutbuilder;

import com.swinglayoutbuilder.constraintlayout.Edge;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Window;

public class SwingLayoutBuilderTest {
    @Test
    @Ignore
    public void borderLayout() {
        JFrame frame = createJFrame("Border layout");
        new SwingLayoutBuilder(frame).borderLayout()
                .gapBetweenComponents(5, 5)
                .addToTop(createColorBlock("Top", Color.lightGray))
                .addToRight(createColorBlock("Right", Color.CYAN))
                .addToBottom(createColorBlock("Bottom", Color.GREEN))
                .addToLeft(createColorBlock("Left", Color.YELLOW))
                .addToCenter(createColorBlock("Center", Color.WHITE))
                .preferredSize(300, 300)
                .finish();
        showFrame(frame);
    }

    @Test
    @Ignore
    public void rowWithWrappingLayout() {
        JFrame frame = createJFrame("Row with wrapping layout");
        new SwingLayoutBuilder(frame).rowWithWrappingLayout()
                .gapBetweenComponents(5, 5)
                .rowAlignmentRight()
                .baseLineAlignment(true)
                .componentPlacementDirection(ComponentOrientation.RIGHT_TO_LEFT)
                .add(createColorBlock("Comp1", Color.LIGHT_GRAY))
                .add(createColorBlock("Comp2", Color.CYAN))
                .add(createColorBlock("Comp3", Color.GREEN))
                .finish();
        showFrame(frame);
    }

    @Test
    @Ignore
    public void formLayout() {
        JTextField lastNameTf = new JTextField("Last Name");
        lastNameTf.setFont(lastNameTf.getFont().deriveFont(30.f));
        JFrame frame = createJFrame("Form layout");
        new SwingLayoutBuilder(frame).formLayout()
                .gapLabelToComponent(10)
                .gapBetweenRows(10)
                .gapBetweenColumns(10)
                .labelsRightAlignment()
                .add("First Name:", new JTextField("First name"))
                .add("Last Name:", lastNameTf)
                .add("Age:", new JTextField("Age"))
                .add(new JCheckBox("My check box"))
                .newColumn()
                .add("City:", new JTextField("City"))
                .add("Street:", new JTextField("Street"))
                .add("House:", new JTextField("House"))
                .add("Apartment:", new JTextField("Apartment"))
                .finish();
        showFrame(frame);
    }

    @Test
    @Ignore
    public void constraintLayout() {
        JFrame frame = createJFrame("Constraint layout");
        new SwingLayoutBuilder(frame).constraintLayout()
                .add(createColorBlock("1 component", Color.WHITE)).preferredSize(100, 100)
                .add(createColorBlock("2 component", Color.YELLOW)).preferredSize(100, 100)
                .linkToPrevious(Edge.TOP, Edge.BOTTOM, 5)
                .add(createColorBlock("Center component", Color.GREEN)).preferredSize(100, 100)
                .linkToParent(Edge.HOR_CENTER, Edge.HOR_CENTER, 0)
                .linkToParent(Edge.VER_CENTER, Edge.VER_CENTER, 0)
                .add(new JLabel("4 component"))
                .linkToPrevious(Edge.BASELINE, Edge.BASELINE, 0)
                .linkToPrevious(Edge.LEFT, Edge.RIGHT, 5)

                .add(createColorBlock("Bottom component", Color.CYAN))
                .linkToParent(Edge.BOTTOM, Edge.BOTTOM, 1)
                .linkToParent(Edge.LEFT, Edge.LEFT, 10)
                .linkToParent(Edge.RIGHT, Edge.RIGHT, -10)

                .add("square", createColorBlock("Square component", Color.MAGENTA)).preferredSize(100, 5)
                .linkToParent(Edge.RIGHT, Edge.RIGHT, 1)
                .linkToId(Edge.HEIGHT, "square", Edge.WIDTH, 0)
                .finish();
        showFrame(frame);
    }

    private JComponent createColorBlock(String message, Color color) {
        JLabel label = new JLabel(message);
        label.setOpaque(true);
        label.setBackground(color);
        return label;
    }

    private JFrame createJFrame(String title) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle(title);
        return frame;
    }

    private void showFrame(Window frame) {
        frame.pack();
        frame.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
        try {
            Thread.sleep(999999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}