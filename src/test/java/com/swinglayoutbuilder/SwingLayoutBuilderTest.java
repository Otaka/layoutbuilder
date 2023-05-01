package com.swinglayoutbuilder;

import com.swinglayoutbuilder.rulelayout.Edge;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
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
                .add(setPreferredSize(createColorBlock("Comp1", Color.LIGHT_GRAY), 40, 50))
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
    public void ruleLayout() {
        JFrame frame = createJFrame("Constraint layout");
        new SwingLayoutBuilder(frame).ruleLayout().parentPadding(10, 10, 10, 10)
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
        new SwingLayoutBuilder(frame).ruleLayout().debug(true).parentPadding(10, 10, 10, 10)
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
    public void constraintLayout_formTemplate() {
        JFrame frame = createJFrame("Constraint layout. Form Template");
        new SwingLayoutBuilder(frame).ruleLayout().debug(true).parentPadding(10, 10, 10, 10)
                .templateForm(5, (t, ruleLayout) -> {
                    t.setAlignLabelsLeft(false);
                    t.setRowsGap(5);
                    t.addRow("label1 jhkh", setPreferredSize(createColorBlock("component1", Color.GREEN), 100, 50));
                    ruleLayout.anchorCurrentComponentEdgesToParentMovingEdges(false, false, false, false);
                    t.addRow("label2", setPreferredSize(new JEditorPane("text/plain", "hello world"), 300, 100))
                            .setLabelToFieldVerticalAlignment(RuleLayoutBuilder.FormRowAlignment.TOP);
                    t.addRow("la3", setPreferredSize(createColorBlock("component3", Color.BLUE), 100, 30));
                }).moveToParent(Edge.LEFT, Edge.LEFT, 0)
                .finish();
        showFrame(frame);
    }

    @Test
    @Ignore
    public void constraintLayout_CenterGroup() {
        JFrame frame = createJFrame("Constraint layout. Center group");
        new SwingLayoutBuilder(frame).ruleLayout().preferredSize(300, 300)
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

    @Test
    @Ignore
    public void constraintLayout_padding() {
        JFrame frame = createJFrame("Constraint layout. Padding");
        new SwingLayoutBuilder(frame).ruleLayout().parentPadding(10, 20, 30, 40)
                .add(createColorBlock("A", Color.GREEN)).preferredSize(150, 150)
                .moveToParent(Edge.LEFT, Edge.LEFT, 0)
                .finish();
        showFrame(frame);
    }

    @Test
    @Ignore
    public void constraintLayout_resizing_anchors() {
        JFrame frame = createJFrame("Constraint layout. Anchor and resize");
        new SwingLayoutBuilder(frame).ruleLayout().parentPadding(10, 20, 30, 40)
                .add(createColorBlock("A", Color.GREEN)).preferredSize(150, 150)
                .moveToParent(Edge.LEFT, Edge.LEFT, 0)
                .anchorCurrentComponentEdgesToParentMovingEdges(false, false, true, true, 0.5f, 0.5f, 1f, 0.5f)
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

    public JComponent setPreferredSize(JComponent component, int width, int height) {
        component.setPreferredSize(new Dimension(width, height));
        return component;
    }

    private void showFrame(final Window frame) {
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