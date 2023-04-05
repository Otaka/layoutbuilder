package com.swinglayoutbuilder;

import com.swinglayoutbuilder.rulelayout.Edge;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

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
        new SwingLayoutBuilder(frame).ruleLayout()
                .add(createColorBlock("1 component", Color.WHITE)).preferredSize(100, 100)
                .add(createColorBlock("2 component", Color.YELLOW)).preferredSize(100, 100)
                .moveToPrevious(Edge.TOP, Edge.BOTTOM, 5)
                .add(createColorBlock("Center component", Color.GREEN)).preferredSize(100, 100)
                .moveToParent(Edge.HOR_CENTER, Edge.HOR_CENTER, 0)
                .moveToParent(Edge.VER_CENTER, Edge.VER_CENTER, 0)
                .add(new JLabel("4 component"))
                .moveToPrevious(Edge.BASELINE, Edge.BASELINE, 0)
                .moveToPrevious(Edge.LEFT, Edge.RIGHT, 5)

                .add(createColorBlock("Bottom component", Color.CYAN))
                .moveToParent(Edge.BOTTOM, Edge.BOTTOM, 1)
                .moveToParent(Edge.LEFT, Edge.LEFT, 10)
                .moveToParent(Edge.RIGHT, Edge.RIGHT, -10)

                .add("square", createColorBlock("Square component", Color.MAGENTA)).preferredSize(100, 5)
                .moveToParent(Edge.RIGHT, Edge.RIGHT, 1)
                .moveToId(Edge.HEIGHT, "square", Edge.WIDTH, 0)
                .finish();
        showFrame(frame);
    }

    @Test
    @Ignore
    public void constraintLayout_center() {
        JFrame frame = createJFrame("Constraint layout center");
        new SwingLayoutBuilder(frame).ruleLayout().preferredSize(200, 200)
                .add(createColorBlock("1 component", Color.WHITE))

                .moveToParent(Edge.HOR_CENTER, Edge.HOR_CENTER, 0)
                .moveToParent(Edge.VER_CENTER, Edge.VER_CENTER, 0)
                .finish();
        showFrame(frame);
    }

    @Test
    @Ignore
    public void constraintLayout_form() {
        JFrame frame = createJFrame("Constraint layout. Form");
        new SwingLayoutBuilder(frame).ruleLayout().debug(true).globalMargin(10, 10, 10, 10).gapBetweenComponents(10)
                .createGroup("mygroup")
                .add(new JLabel("mylabel"))
                .addToCurrentGroup()
                .moveToParent(Edge.LEFT, Edge.LEFT, 0)//move the component to left top position
                .moveToParent(Edge.TOP, Edge.TOP, 0)
                .add(new JTextArea("2 component")).preferredSize(100, 100)
                .addToCurrentGroup()
                .moveToPrevious(Edge.BOTTOM, Edge.BOTTOM, 0)//align text field to the label bottom position(just to show how group moving works)
                .moveToPrevious(Edge.LEFT, Edge.RIGHT)//and put it right from the label
                .moveIdToParent("mygroup", Edge.TOP, Edge.TOP, 0)//adjust group position

                .finish();
        showFrame(frame);
    }

    @Test
    @Ignore
    public void constraintLayout_CenterGroup() {
        JFrame frame = createJFrame("Constraint layout. Center group");
        new SwingLayoutBuilder(frame).ruleLayout().gapBetweenComponents(0).preferredSize(300, 300)
                .createGroup("mygroup")
                .add(createColorBlock("A", Color.GREEN)).preferredSize(50, 50)
                .addToCurrentGroup()
                .add(createColorBlock("B", Color.RED)).preferredSize(25, 25)
                .addToCurrentGroup()
                .moveToPrevious(Edge.LEFT, Edge.RIGHT, 5)
                .moveToPrevious(Edge.TOP, Edge.BOTTOM, 5)
                .moveIdToParent("mygroup", Edge.HOR_CENTER, Edge.HOR_CENTER)
                .moveIdToParent("mygroup", Edge.VER_CENTER, Edge.VER_CENTER)
                .finish();
        showFrame(frame);
    }

    private JComponent createColorBlock(String message, Color color) {
        JLabel label = new JLabel(message);
        label.setOpaque(true);
        label.setBackground(color);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JFrame createJFrame(String title) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle(title);
        return frame;
    }

    private void showFrame(final Window frame) {
        frame.pack();
        frame.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        });
        try {
            Thread.sleep(999999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}