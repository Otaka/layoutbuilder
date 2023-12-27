package com.swinglayoutbuilder;

import com.swinglayoutbuilder.mig.MigDock;
import com.swinglayoutbuilder.mig.MigTag;
import org.junit.Ignore;
import org.junit.Test;

import static java.awt.Color.BLUE;
import static java.awt.Color.GRAY;
import static java.awt.Color.GREEN;
import static java.awt.Color.ORANGE;
import static java.awt.Color.PINK;
import static java.awt.Color.YELLOW;

public class MigLayoutBuilderTest extends SwingLayoutBuilderTest {
    @Test
    @Ignore
    public void borderLayout() {
        runInWindow((frame) -> {
            createLayoutBuilder(frame).migLayout().layoutNoGrid()
                    .add(createColorBlock("North", BLUE)).dock(MigDock.NORTH)
                    .add(createColorBlock("Center", YELLOW)).dock(MigDock.CENTER).width(200).height(200)
                    .add(createColorBlock("South", ORANGE)).dock(MigDock.SOUTH)
                    .add(createColorBlock("West", GREEN)).dock(MigDock.WEST)
                    .add(createColorBlock("East", PINK)).dock(MigDock.EAST)
                    .finish();
        });
    }

    @Test
    @Ignore
    public void rowSizeGroupEqualSize() {
        runInWindow((frame) -> {
            createLayoutBuilder(frame).migLayout()
                    .add(createColorBlock("1", BLUE)).sizeGroup("g1").width(100).height(50)
                    .add(createColorBlock("2", YELLOW)).sizeGroup("g1")
                    .add(createColorBlock("3", ORANGE)).sizeGroup("g1")
                    .add(createColorBlock("4", GREEN)).sizeGroup("g1")
                    .add(createColorBlock("5", PINK)).sizeGroup("g1")
                    .finish();
        });
    }

    @Test
    @Ignore
    public void percents() {
        runInWindow((frame) -> {
            createLayoutBuilder(frame).migLayout().layoutInsets(0)
                    .add(createColorBlock("half of parent", BLUE)).width("50%").height("50%").x2("100%").y2("100%")
                    .finish();
        });
    }

    @Test
    @Ignore
    public void buttonsTags() {
        //order of the buttons should be the same in both windows(depends on OS), even if they are declared in different order in code
        runInWindow((frame) -> {
            createLayoutBuilder(frame).migLayout().layoutVertical()
                    .add(createLayoutBuilder(createPanel(frame, GRAY)).migLayout().layoutNoGrid()
                            .add(createButton("Cancel")).width(100).tag(MigTag.CANCEL)
                            .add(createButton("OK")).width(100).tag(MigTag.OK)
                            .finish())
                    .add(createLayoutBuilder(createPanel(frame, YELLOW)).migLayout().layoutNoGrid()
                            .add(createButton("OK")).width(100).tag(MigTag.OK)
                            .add(createButton("Cancel")).width(100).tag(MigTag.CANCEL)
                            .finish())
                    .finish();
        });
    }

    @Test
    @Ignore
    public void layoutBottomTop() {
        runInWindow((frame) -> {
            createLayoutBuilder(frame).migLayout().layoutVertical().layoutBottomToTop()
                    .label("first")
                    .label("second")
                    .finish();
        });
    }
}
