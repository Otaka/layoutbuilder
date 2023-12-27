package com.swinglayoutbuilder.mig;

import java.util.HashSet;
import java.util.StringJoiner;

@SuppressWarnings("unused")
public class MigLayoutBuilder extends MigLayoutBuilderBase {
    private final HashSet<String> ids = new HashSet<>();
    private MigUnits defaultUnits = MigUnits.Pixels;

    public MigLayoutBuilder setDefaultUnits(MigUnits defaultUnits) {
        this.defaultUnits = defaultUnits;
        return this;
    }

    public MigLayoutBuilder rowConstraint(java.util.function.Consumer<MigRowColumnConfigBuilder> constraintSetter) {
        checkLayoutAlreadyInitialized();
        MigRowColumnConfigBuilder builder = new MigRowColumnConfigBuilder();
        constraintSetter.accept(builder);
        rowConstraints.append(builder.finish());
        return this;
    }

    public MigLayoutBuilder columnConstraint(java.util.function.Consumer<MigRowColumnConfigBuilder> constraintSetter) {
        checkLayoutAlreadyInitialized();
        MigRowColumnConfigBuilder builder = new MigRowColumnConfigBuilder();
        constraintSetter.accept(builder);
        columnConstraints.append(builder.finish());
        return this;
    }

    /**
     * Sets auto-wrap mode for the layout. This means that the grid will wrap to a new column/row after a certain number of columns (for horizontal flow) or rows (for vertical flow). The number is either specified as an integer after the keyword or if not, the number of column/row constraints specified will be used. A wrapping layout means that after the count:th component has been added the layout will wrap and continue on the next row/column. If wrap is turned off (default) the Component Constraint's "wrap" can be used to control wrapping.
     */
    public MigLayoutBuilder layoutWrap() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("wrap");
        return this;
    }

    /**
     * Sets auto-wrap mode for the layout. This means that the grid will wrap to a new column/row after a certain number of columns (for horizontal flow) or rows (for vertical flow). The number is either specified as an integer after the keyword or if not, the number of column/row constraints specified will be used. A wrapping layout means that after the count:th component has been added the layout will wrap and continue on the next row/column. If wrap is turned off (default) the Component Constraint's "wrap" can be used to control wrapping.
     */
    public MigLayoutBuilder layoutWrap(int count) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("wrap " + count);
        return this;
    }

    /**
     * Specifies the default gap between the cells in the grid and are thus overriding the platform default value.<br>
     * A single value sets only the preferred size and is exactly the same as "null:10:null" and ":10:" and "n:10:n"
     */
    public MigLayoutBuilder layoutGapX(float gap) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("gapx " + floatToUnit(gap));
        return this;
    }

    /**
     * Specifies the default gap between the cells in the grid and are thus overriding the platform default value. The gaps are specified as a BoundSize.<br>
     * A bound size is a size that optionally has a lower and/or upper bound. Practically it is a minimum/preferred/maximum size combination but none of the sizes are actually mandatory. If a size is missing (e.g. the preferred) it is null and will be replaced by the most appropriate value. For components this value is the corresponding size (E.g. Component.getPreferredSize() on Swing) and for columns/rows it is the size of the components in the row (see min/pref/max in UnitValue above).<br>
     * <p>
     * The format is "min:preferred:max", however there are shorter versions since for instance it is seldom needed to specify the maximum size.<br>
     * <p>
     * A single value (E.g. "10") sets only the preferred size and is exactly the same as "null:10:null" and ":10:" and "n:10:n".<br>
     * Two values (E.g. "10:20") means minimum and preferred size and is exactly the same as "10:20:null" and "10:20:" and "10:20:n"<br>
     * The use a of an exclamation mark (E.g. "20!") means that the value should be used for all size types and no colon may then be used in the string. It is the same as "20:20:20".<br>
     */
    public MigLayoutBuilder layoutGapX(String gapExpression) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("gapx " + gapExpression);
        return this;
    }

    /**
     * Specifies the default gap between the cells in the grid and are thus overriding the platform default value.<br>
     * A single value sets only the preferred size and is exactly the same as "null:10:null" and ":10:" and "n:10:n"
     */
    public MigLayoutBuilder layoutGapY(float gap) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("gapy " + floatToUnit(gap));
        return this;
    }

    /**
     * Specifies the default gap between the cells in the grid and are thus overriding the platform default value. The gaps are specified as a BoundSize.<br>
     * A bound size is a size that optionally has a lower and/or upper bound. Practically it is a minimum/preferred/maximum size combination but none of the sizes are actually mandatory. If a size is missing (e.g. the preferred) it is null and will be replaced by the most appropriate value. For components this value is the corresponding size (E.g. Component.getPreferredSize() on Swing) and for columns/rows it is the size of the components in the row (see min/pref/max in UnitValue above).<br>
     * <p>
     * The format is "min:preferred:max", however there are shorter versions since for instance it is seldom needed to specify the maximum size.<br>
     * <p>
     * A single value (E.g. "10") sets only the preferred size and is exactly the same as "null:10:null" and ":10:" and "n:10:n".<br>
     * Two values (E.g. "10:20") means minimum and preferred size and is exactly the same as "10:20:null" and "10:20:" and "10:20:n"<br>
     * The use a of an exclamation mark (E.g. "20!") means that the value should be used for all size types and no colon may then be used in the string. It is the same as "20:20:20".<br>
     */
    public MigLayoutBuilder layoutGapY(String gapExpression) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("gapy " + gapExpression);
        return this;
    }

    /**
     * Turns on debug painting for the container. This will lead to an active repaint every second.
     */
    public MigLayoutBuilder layoutDebug() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("debug");
        return this;
    }

    /**
     * Puts the layout in a flow-only mode. All components in the flow direction will be put in the same cell and will thus not be aligned with component in other rows/columns. For normal horizontal flow this is the same as to say that all component will be put in the first and only column.
     */
    public MigLayoutBuilder layoutNoGrid() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("nogrid");
        return this;
    }

    /**
     * Turns off padding of visual bounds (e.g. compensation for drop shadows)
     */
    public MigLayoutBuilder layoutNoVisualPadding() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("novisualpadding");
        return this;
    }

    /**
     * Claims all available space in the container for the columns and/or rows. At least one component need to have a "grow" constaint for it to fill the container. The space will be divided equal, though honoring "growpriority". If no columns/rows has "grow" set the grow weight of the components in the rows/columns will migrate to that row/column.
     */
    public MigLayoutBuilder layoutFill() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("fill");
        return this;
    }

    /**
     * Claims all available space in the container for the columns and/or rows. At least one component need to have a "grow" constaint for it to fill the container. The space will be divided equal, though honoring "growpriority". If no columns/rows has "grow" set the grow weight of the components in the rows/columns will migrate to that row/column.
     */
    public MigLayoutBuilder layoutFillX() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("fillx");
        return this;
    }

    /**
     * Claims all available space in the container for the columns and/or rows. At least one component need to have a "grow" constaint for it to fill the container. The space will be divided equal, though honoring "growpriority". If no columns/rows has "grow" set the grow weight of the components in the rows/columns will migrate to that row/column.
     */
    public MigLayoutBuilder layoutFillY() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("filly");
        return this;
    }

    /**
     * Specified the insets for the laid out container. The gaps before/after the first/last column/row is always discarded and replaced by these layout insets. This is the same thing as setting an EmptyBorder on the container but without removing any border already there. Default value is "panel" (or zero if there are docking components). The size of "dialog" and "panel" insets is returned by the current PlatformConverter
     */
    public MigLayoutBuilder layoutInsetsPanel() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("insets panel");
        return this;
    }

    /**
     * Specified the insets for the laid out container. The gaps before/after the first/last column/row is always discarded and replaced by these layout insets. This is the same thing as setting an EmptyBorder on the container but without removing any border already there. The size of "dialog" and "panel" insets is returned by the current PlatformConverter
     */
    public MigLayoutBuilder layoutInsetsDialog() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("insets dialog");
        return this;
    }

    /**
     * Specified the insets for the laid out container. The gaps before/after the first/last column/row is always discarded and replaced by these layout insets. This is the same thing as setting an EmptyBorder on the container but without removing any border already there. The inset values all around can also be set explicitly for one or more sides. Insets on sides that are set to "null" or "n" will get the default values provided by the PlatformConverter. If less than four sides are specified the last value will be used for the remaining side
     */
    public MigLayoutBuilder layoutInsets(float... values) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("insets " + floatsToUnit(values));
        return this;
    }

    /**
     * Specified the insets for the laid out container. The gaps before/after the first/last column/row is always discarded and replaced by these layout insets. This is the same thing as setting an EmptyBorder on the container but without removing any border already there. The inset values all around can also be set explicitly for one or more sides. Insets on sides that are set to "null" or "n" will get the default values provided by the PlatformConverter. If less than four sides are specified the last value will be used for the remaining side
     */
    public MigLayoutBuilder layoutInsets(String... values) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("insets " + joinStrings(" ", values));
        return this;
    }

    /**
     * Puts the layout in vertical flow mode.<br>
     * This means that the next cell is normally below and the next component will be put there instead of to the right.<br>
     * The same as layoutFlowY()
     */
    public MigLayoutBuilder layoutVertical() {
        return layoutFlowY();
    }

    /**
     * Puts the layout in vertical flow mode. This means that the next cell is normally below and the next component will be put there instead of to the right.
     */
    public MigLayoutBuilder layoutFlowY() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("flowy");
        return this;
    }

    /**
     * Specifies the alignment for the laid out components as a group. If the total bounds of all laid out components does not fill the entire container the align value is used to position the components within the container without changing their relative positions.
     */
    public MigLayoutBuilder layoutAlignX(float units) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("alignx " + floatToUnit(units));
        return this;
    }

    /**
     * Specifies the alignment for the laid out components as a group. If the total bounds of all laid out components does not fill the entire container the align value is used to position the components within the container without changing their relative positions.
     */
    public MigLayoutBuilder layoutAlignX(String value) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("alignx " + value);
        return this;
    }

    /**
     * Specifies the alignment for the laid out components as a group. If the total bounds of all laid out components does not fill the entire container the align value is used to position the components within the container without changing their relative positions.
     */
    public MigLayoutBuilder layoutAlignX(MigAlign align) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("alignx " + align.getValue());
        return this;
    }

    /**
     * Specifies the alignment for the laid out components as a group. If the total bounds of all laid out components does not fill the entire container the align value is used to position the components within the container without changing their relative positions.
     */
    public MigLayoutBuilder layoutAlignY(float units) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("aligny " + floatToUnit(units));
        return this;
    }

    /**
     * Specifies the alignment for the laid out components as a group. If the total bounds of all laid out components does not fill the entire container the align value is used to position the components within the container without changing their relative positions.
     */
    public MigLayoutBuilder layoutAlignY(String value) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("aligny " + value);
        return this;
    }

    /**
     * Specifies the alignment for the laid out components as a group. If the total bounds of all laid out components does not fill the entire container the align value is used to position the components within the container without changing their relative positions.
     */
    public MigLayoutBuilder layoutAlignY(MigAlign align) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("aligny " + align.getValue());
        return this;
    }

    /**
     * Overrides the container's ComponentOrientation property for this layout. Normally this value is dependant on the Locale that the application is running. This constraint overrides that value.
     */
    public MigLayoutBuilder layoutLeftToRight() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("ltr");
        return this;
    }

    /**
     * Overrides the container's ComponentOrientation property for this layout. Normally this value is dependant on the Locale that the application is running. This constraint overrides that value.
     */
    public MigLayoutBuilder layoutRightToLeft() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("rtl");
        return this;
    }

    /**
     * Specifies if the components should be added in the grid bottom-to-top or top-to-bottom. This value is not picked up from the container and is top-to-bottom by default.
     */
    public MigLayoutBuilder layoutTopToBottom() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("ttb");
        return this;
    }

    /**
     * Specifies if the components should be added in the grid bottom-to-top or top-to-bottom. This value is not picked up from the container and is top-to-bottom by default.
     */
    public MigLayoutBuilder layoutBottomToTop() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("btt");
        return this;
    }

    /**
     * Sets the default hide mode for the layout. This hide mode can be overridden by the component constraint. The hide mode specified how the layout manager should handle a component that isn't visible. The modes are:
     */
    public MigLayoutBuilder layoutHideMode(MigHideMode mode) {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("hidemode " + mode.getOrdinal());
        return this;
    }

    /**
     * Instructs the layout engine to not use caches. This should normally only be needed if the "%" unit is used as it is a function of the parent size. If you are experiencing revalidation problems you can try to set this constraint.
     */
    public MigLayoutBuilder layoutNoCache() {
        checkLayoutAlreadyInitialized();
        addLayoutConstraint("nocache");
        return this;
    }


    //COMPONENT CONSTRAINTS

    /**
     * Wraps to a new column/row after the component has been put in the next available cell. This means that the next component will be put on the new row/column. Tip! Read wrap as "wrap after".
     */
    public MigLayoutBuilder wrap() {
        addComponentConstraint("wrap");
        return this;
    }

    /**
     * Wraps to a new column/row after the component has been put in the next available cell. This means that the next component will be put on the new row/column. Tip! Read wrap as "wrap after".<br>
     * Specified "gapsize" will override the size of the gap between the current and next row (or column if "flowy"). Note that the gaps size is after the row that this component will end up at.
     */
    public MigLayoutBuilder wrap(float gapSize) {
        addComponentConstraint("wrap " + floatsToUnit(gapSize));
        return this;
    }

    /**
     * Wraps to a new column/row after the component has been put in the next available cell. This means that the next component will be put on the new row/column. Tip! Read wrap as "wrap after".<br>
     * Specified "gapsize" will override the size of the gap between the current and next row (or column if "flowy"). Note that the gaps size is after the row that this component will end up at.
     */
    public MigLayoutBuilder wrap(String gapSize) {
        addComponentConstraint("wrap " + gapSize);
        return this;
    }

    /**
     * Wraps to a new column/row before the component is put in the next available cell. This means that the this component will be put on a new row/column. Tip! Read wrap as "on a newline".
     */
    public MigLayoutBuilder newline() {
        addComponentConstraint("newline");
        return this;
    }

    /**
     * Wraps to a new column/row before the component is put in the next available cell. This means that the this component will be put on a new row/column. Tip! Read wrap as "on a newline". Specified "gapsize" will override the size of the gap between the current and next row (or column if "flowy"). Note that the gaps size is before the row that this component will end up at.
     */
    public MigLayoutBuilder newline(float gapSize) {
        addComponentConstraint("newline " + floatsToUnit(gapSize));
        return this;
    }

    /**
     * Wraps to a new column/row before the component is put in the next available cell. This means that the this component will be put on a new row/column. Tip! Read wrap as "on a newline". Specified "gapsize" will override the size of the gap between the current and next row (or column if "flowy"). Note that the gaps size is before the row that this component will end up at.
     */
    public MigLayoutBuilder newline(String gapSize) {
        addComponentConstraint("newline " + gapSize);
        return this;
    }

    /**
     * Makes the row and/or column that the component is residing in grow with "weight". This can be used instead of having a "grow" keyword in the column/row constraints.
     */
    public MigLayoutBuilder pushX() {
        addComponentConstraint("pushx");
        return this;
    }

    /**
     * Makes the row and/or column that the component is residing in grow with "weight". This can be used instead of having a "grow" keyword in the column/row constraints.
     */
    public MigLayoutBuilder pushX(float weightX) {
        addComponentConstraint("pushx " + weightX);
        return this;
    }

    /**
     * Makes the row and/or column that the component is residing in grow with "weight". This can be used instead of having a "grow" keyword in the column/row constraints.
     */
    public MigLayoutBuilder pushY() {
        addComponentConstraint("pushy");
        return this;
    }

    /**
     * Makes the row and/or column that the component is residing in grow with "weight". This can be used instead of having a "grow" keyword in the column/row constraints.
     */
    public MigLayoutBuilder pushY(float weightY) {
        addComponentConstraint("pushy " + weightY);
        return this;
    }

    /**
     * Skips a number of cells in the flow. This is used to jump over a number of cells before the next free cell is looked for. The skipping is done before this component is put in a cell and thus this cells is affected by it. "count" defaults to 1 if not specified.
     */
    public MigLayoutBuilder skip() {
        addComponentConstraint("skip");
        return this;
    }

    /**
     * Skips a number of cells in the flow. This is used to jump over a number of cells before the next free cell is looked for. The skipping is done before this component is put in a cell and thus this cells is affected by it.
     */
    public MigLayoutBuilder skip(int count) {
        addComponentConstraint("skip " + count);
        return this;
    }

    /**
     * Spans the current cell (merges) over a number of cells.<br>
     * Practically this means that this cell and the count number of cells will be treated as one cell and the component can use the space that all these cells have.
     * Note that a cell can be spanned and split at the same time, so it can for instance be spanning 2 cells and split that space for three components.<br>
     * "span" for the first cell in a row is the same thing as setting "nogrid" in the row constraint.
     */
    public MigLayoutBuilder spanX(int count) {
        addComponentConstraint("spanx " + count);
        return this;
    }

    /**
     * Spans the current cell (merges) over a number of cells.<br>
     * Practically this means that this cell and the count number of cells will be treated as one cell and the component can use the space that all these cells have.
     * Note that a cell can be spanned and split at the same time, so it can for instance be spanning 2 cells and split that space for three components.<br>
     * "span" for the first cell in a row is the same thing as setting "nogrid" in the row constraint.
     */
    public MigLayoutBuilder spanY(int count) {
        addComponentConstraint("spany " + count);
        return this;
    }

    /**
     * Splits the cell in a number of sub-cells.<br>
     * Practically this means that the next count number components will be put in the same cell, next to each other without gaps.<br>
     * Only the first component in a cell can set the split, any subsequent split keywords in the cell will be ignored.<br>
     * count defaults to infinite if not specified which means that split alone will put all coming components in the same cell.<br>
     * "split", "wrap" or "newline" will break out of the split celll. The latter two will move to a new row/column as usual.<br>
     * Note! "skip" will will skip out if the splitting and continue in the next cell.
     */
    public MigLayoutBuilder split(int count) {
        addComponentConstraint("split " + count);
        return this;
    }

    /**
     * Sets the grid cell that the component should be placed in.<br>
     * If there are already components in the cell they will share the cell.<br>
     * If there are two integers specified they will be interpreted as absolute coordinates for the column and row.<br>
     * The flow will continue after this cell.
     */
    public MigLayoutBuilder cell(int col, int row) {
        addComponentConstraint("cell " + col + " " + row);
        return this;
    }

    /**
     * Sets the grid cell that the component should be placed in.<br>
     * If there are already components in the cell they will share the cell.<br>
     * If there are two integers specified they will be interpreted as absolute coordinates for the column and row.<br>
     * The flow will continue after this cell.<br>
     * How many cells that will be spanned is optional but may be specified. It is the same thing as using the spanx and spany keywords.
     */
    public MigLayoutBuilder cell(int col, int row, int spanX) {
        addComponentConstraint("cell " + col + " " + row + " " + spanX);
        return this;
    }

    /**
     * Sets the grid cell that the component should be placed in.<br>
     * If there are already components in the cell they will share the cell.<br>
     * If there are two integers specified they will be interpreted as absolute coordinates for the column and row.<br>
     * The flow will continue after this cell.<br>
     * How many cells that will be spanned is optional but may be specified. It is the same thing as using the spanx and spany keywords.
     */
    public MigLayoutBuilder cell(int col, int row, int spanX, int spanY) {
        addComponentConstraint("cell " + col + " " + row + " " + spanX + " " + spanY);
        return this;
    }

    /**
     * Sets the flow direction in the cell.<br>
     * By default the flow direction in the cell is the same as the flow direction for the layout.<br>
     * So if the components flows from left to right they will do so for in-cell flow as well.<br>
     * The first component added to a cell can change the cell flow.<br>
     * If flow direction is changed to flowy the components in the cell will be positioned above/under each other
     */
    public MigLayoutBuilder flowX() {
        addComponentConstraint("flowx");
        return this;
    }

    /**
     * Sets the flow direction in the cell.<br>
     * By default the flow direction in the cell is the same as the flow direction for the layout.<br>
     * So if the components flows from left to right they will do so for in-cell flow as well.<br>
     * The first component added to a cell can change the cell flow.<br>
     * If flow direction is changed to flowy the components in the cell will be positioned above/under each other
     */
    public MigLayoutBuilder flowY() {
        addComponentConstraint("flowy");
        return this;
    }

    /**
     * Overrides the default size of the component that is set by the UI delegate or by the developer explicitly on the component.
     */
    public MigLayoutBuilder width(float width) {
        addComponentConstraint("width " + floatToUnit(width));
        return this;
    }

    /**
     * Overrides the default size of the component that is set by the UI delegate or by the developer explicitly on the component.<br>
     * The size is specified as a BoundSize. See the Common Argument Types section above for an explanation.<br>
     * Note that expressions is supported and you can for instance set the size for a component with "width pref+10px" to make it 10 pixels larger than normal or "width max(100, 10%)" to make it 10% of the container's width, but a maximum of 100 pixels.<br>
     * Example: "width 10!" or "width 10" or or "w min:100:pref" or "w 100!" or "width visual.x2-pref".<br>
     */
    public MigLayoutBuilder width(String width) {
        addComponentConstraint("width " + width);
        return this;
    }

    /**
     * Overrides the default size of the component that is set by the UI delegate or by the developer explicitly on the component.
     */
    public MigLayoutBuilder height(float height) {
        addComponentConstraint("height " + floatToUnit(height));
        return this;
    }

    /**
     * Overrides the default size of the component that is set by the UI delegate or by the developer explicitly on the component.<br>
     * The size is specified as a BoundSize. See the Common Argument Types section above for an explanation.<br>
     * Note that expressions is supported and you can for instance set the size for a component with "height pref+10px" to make it 10 pixels larger than normal or "height max(100, 10%)" to make it 10% of the container's height, but a maximum of 100 pixels.<br>
     * Example: "height 10!" or "height 10" or "height visual.x2-pref".<br>
     */
    public MigLayoutBuilder height(String height) {
        addComponentConstraint("height " + height);
        return this;
    }

    /**
     * Overrides the default size of the component for minimum size that is set by the UI delegate or by the developer explicitly on the component.<br>
     * The size is specified as a BoundSize. See the Common Argument Types section above for an explanation.<br>
     * Note that expressions is supported and you can for instance set the size for a component with "wmin pref-10px" to make it no less than 10 pixels smaller than normal.<br>
     * These keywords are syntactic shorts for "width size:pref" or "width min:pref:size" with is exactly the same for minimum.<br>
     * Example: "wmin 10" or "wmin pref+100".
     */
    public MigLayoutBuilder widthMinimum(float width) {
        addComponentConstraint("wmin " + floatToUnit(width));
        return this;
    }

    /**
     * Overrides the default size of the component for minimum size that is set by the UI delegate or by the developer explicitly on the component.<br>
     * The size is specified as a BoundSize. See the Common Argument Types section above for an explanation.<br>
     * Note that expressions is supported and you can for instance set the size for a component with "wmin pref-10px" to make it no less than 10 pixels smaller than normal.<br>
     * These keywords are syntactic shorts for "width size:pref" or "width min:pref:size" with is exactly the same for minimum.<br>
     * Example: "wmin 10" or "wmin pref+100".
     */
    public MigLayoutBuilder widthMinimum(String width) {
        addComponentConstraint("wmin " + width);
        return this;
    }


    /**
     * Overrides the default size of the component for maximum size that is set by the UI delegate or by the developer explicitly on the component.<br>
     * The size is specified as a BoundSize. See the Common Argument Types section above for an explanation.<br>
     * Note that expressions is supported and you can for instance set the size for a component with "wmax pref-10px" to make it no less than 10 pixels smaller than normal.<br>
     * These keywords are syntactic shorts for "width size:pref" or "width max:pref:size" with is exactly the same for maximum.<br>
     * Example: "wmax 10" or "wmax pref+100".
     */
    public MigLayoutBuilder widthMaximum(float width) {
        addComponentConstraint("wmax " + floatToUnit(width));
        return this;
    }

    /**
     * Overrides the default size of the component for maximum size that is set by the UI delegate or by the developer explicitly on the component.<br>
     * Note that expressions is supported and you can for instance set the size for a component with "wmax pref-10px" to make it no less than 10 pixels smaller than normal.<br>
     * These keywords are syntactic shorts for "width size:pref" or "width max:pref:size" with is exactly the same for maximum.<br>
     * Example: "wmax 10" or "wmax pref+100".
     */
    public MigLayoutBuilder widthMaximum(String width) {
        addComponentConstraint("wmax " + width);
        return this;
    }

    /**
     * Overrides the default size of the component for minimum size that is set by the UI delegate or by the developer explicitly on the component.<br>
     * Note that expressions is supported and you can for instance set the size for a component with "hmin pref-10px" to make it no less than 10 pixels smaller than normal.<br>
     * These keywords are syntactic shorts for "height size:pref" with is exactly the same for minimum.<br>
     * Example: "hmin 10" or "hmin pref+100".
     */
    public MigLayoutBuilder heightMinimum(float height) {
        addComponentConstraint("hmin " + floatToUnit(height));
        return this;
    }

    /**
     * Overrides the default size of the component for minimum size that is set by the UI delegate or by the developer explicitly on the component.<br>
     * Note that expressions is supported and you can for instance set the size for a component with "hmin pref-10px" to make it no less than 10 pixels smaller than normal.<br>
     * These keywords are syntactic shorts for "height size:pref" or "height min:pref:size" with is exactly the same for minimum.<br>
     * Example: "hmin 10" or "hmin pref+100".
     */
    public MigLayoutBuilder heightMinimum(String height) {
        addComponentConstraint("hmin " + height);
        return this;
    }

    /**
     * Overrides the default size of the component for maximum size that is set by the UI delegate or by the developer explicitly on the component.<br>
     * The size is specified as a BoundSize. See the Common Argument Types section above for an explanation.<br>
     * Note that expressions is supported and you can for instance set the size for a component with "hmax pref-10px" to make it no less than 10 pixels smaller than normal.<br>
     * These keywords are syntactic shorts for "height size:pref" or "hmax max:pref:size" with is exactly the same for maximum.<br>
     * Example: "hmax 10" or "hmax pref+100".
     */
    public MigLayoutBuilder heightMaximum(float height) {
        addComponentConstraint("hmax " + floatToUnit(height));
        return this;
    }

    /**
     * Overrides the default size of the component for maximum size that is set by the UI delegate or by the developer explicitly on the component.<br>
     * Note that expressions is supported and you can for instance set the size for a component with "hmax pref-10px" to make it no less than 10 pixels smaller than normal.<br>
     * These keywords are syntactic shorts for "height size:pref" or "height max:pref:size" with is exactly the same for maximum.<br>
     * Example: "hmax 10" or "hmax pref+100".
     */
    public MigLayoutBuilder heightMaximum(String height) {
        addComponentConstraint("hmax " + height);
        return this;
    }

    /**
     * Sets how keen the component should be to grow in relation to other component in the same cell. <br>
     * The weight (defaults to 100 if not specified) is purely a relative value to other components' weight. <br>
     * Twice the weight will get double the extra space. <br>
     * If this constraint is not set the grow weight is set to 0 and the component will not grow (unless fill is set in the row/column in which case "grow 0" can be used to explicitly make it not grow). <br>
     * Grow weight will only be compared against the weights in the same grow priority group and for the same cell. See below.
     */
    public MigLayoutBuilder growX() {
        addComponentConstraint("growx");
        return this;
    }

    /**
     * Sets how keen the component should be to grow in relation to other component in the same cell. <br>
     * The weight (defaults to 100 if not specified) is purely a relative value to other components' weight. <br>
     * Twice the weight will get double the extra space. <br>
     * If this constraint is not set the grow weight is set to 0 and the component will not grow (unless fill is set in the row/column in which case "grow 0" can be used to explicitly make it not grow). <br>
     * Grow weight will only be compared against the weights in the same grow priority group and for the same cell. See below.
     */
    public MigLayoutBuilder growX(int weight) {
        addComponentConstraint("growx " + weight);
        return this;
    }

    /**
     * Sets how keen the component should be to grow in relation to other component in the same cell. <br>
     * The weight (defaults to 100 if not specified) is purely a relative value to other components' weight. <br>
     * Twice the weight will get double the extra space. <br>
     * If this constraint is not set the grow weight is set to 0 and the component will not grow (unless fill is set in the row/column in which case "grow 0" can be used to explicitly make it not grow). <br>
     * Grow weight will only be compared against the weights in the same grow priority group and for the same cell. See below.
     */
    public MigLayoutBuilder growY() {
        addComponentConstraint("growy");
        return this;
    }

    /**
     * Sets how keen the component should be to grow in relation to other component in the same cell. <br>
     * The weight (defaults to 100 if not specified) is purely a relative value to other components' weight. <br>
     * Twice the weight will get double the extra space. <br>
     * If this constraint is not set the grow weight is set to 0 and the component will not grow (unless fill is set in the row/column in which case "grow 0" can be used to explicitly make it not grow). <br>
     * Grow weight will only be compared against the weights in the same grow priority group and for the same cell. See below.
     */
    public MigLayoutBuilder growY(int weight) {
        addComponentConstraint("growy " + weight);
        return this;
    }

    /**
     * Sets the grow priority for the component. <br>
     * When growing, all components with higher priorities will be grown to their maximum size before any component with lower priority are considered.<br>
     * The default grow priority is 100.<br>
     * This constraint can be used to make certain components grow to max before other components even start to grow.
     */
    public MigLayoutBuilder growPriorityX(int priority) {
        addComponentConstraint("gpx " + priority);
        return this;
    }

    /**
     * Sets the grow priority for the component. <br>
     * When growing, all components with higher priorities will be grown to their maximum size before any component with lower priority are considered.<br>
     * The default grow priority is 100.<br>
     * This constraint can be used to make certain components grow to max before other components even start to grow.
     */
    public MigLayoutBuilder growPriorityY(int priority) {
        addComponentConstraint("gpy " + priority);
        return this;
    }

    /**
     * Sets how keen/reluctant the component should be to shrink in relation to other components. <br>
     * The weight is purely a relative value to other components' weight. <br>
     * Twice the weight will shrink twice as much when space is scarce. <br>
     * If this constraint is not set the shrink weight defaults to 100, which means that all components by default can shrink to their minimum size, but no less. <br>
     * Shrink weight will only be compared against the weights in the same shrink priority group (other components with the same shrink priority).
     */
    public MigLayoutBuilder shrink(int shrinkX) {
        addComponentConstraint("shrink " + shrinkX);
        return this;
    }

    /**
     * Sets how keen/reluctant the component should be to shrink in relation to other components. <br>
     * The weight is purely a relative value to other components' weight. <br>
     * Twice the weight will shrink twice as much when space is scarce. <br>
     * If this constraint is not set the shrink weight defaults to 100, which means that all components by default can shrink to their minimum size, but no less. <br>
     * Shrink weight will only be compared against the weights in the same shrink priority group (other components with the same shrink priority).
     */
    public MigLayoutBuilder shrink(int shrinkX, int shrinkY) {
        addComponentConstraint("shrink " + shrinkX + " " + shrinkY);
        return this;
    }

    /**
     * Sets the shrink priority for the component.<br>
     * When space is scarce and components needs be be shrunk, all components with higher priorities will be shrunk to their minimum size before any component with lower priority are considered.<br>
     * The default shrink priority is 100.<br>
     * This can be used to make certain components shrink to min before other even start to shrink.
     */
    public MigLayoutBuilder shrinkPriorityX(int weight) {
        addComponentConstraint("shpx " + weight);
        return this;
    }

    /**
     * Sets the shrink priority for the component.<br>
     * When space is scarce and components needs be be shrunk, all components with higher priorities will be shrunk to their minimum size before any component with lower priority are considered.<br>
     * The default shrink priority is 100.<br>
     * This can be used to make certain components shrink to min before other even start to shrink.
     */
    public MigLayoutBuilder shrinkPriorityY(int weight) {
        addComponentConstraint("shpy " + weight);
        return this;
    }

    /**
     * Gives the component a size group name.<br>
     * All components that share a size group name will get the same BoundSize (min/preferred/max).<br>
     * It is used to make sure that all components in the same size group gets the same min/preferred/max size which is that of the largest component in the group.<br>
     * An empty name "" can be used.
     */
    public MigLayoutBuilder sizeGroup(String groupName) {
        addComponentConstraint("sg " + groupName);
        return this;
    }

    /**
     * Gives the component's width a width of group with name.<br>
     * All components that share a size group name will get the same BoundSize (min/preferred/max).<br>
     * It is used to make sure that all components in the same size group gets the same min/preferred/max size which is that of the largest component in the group.<br>
     * An empty name "" can be used.
     */
    public MigLayoutBuilder sizeGroupX(String groupName) {
        addComponentConstraint("sgx " + groupName);
        return this;
    }

    /**
     * Gives the component's height a height of group with name.<br>
     * All components that share a size group name will get the same BoundSize (min/preferred/max).<br>
     * It is used to make sure that all components in the same size group gets the same min/preferred/max size which is that of the largest component in the group.<br>
     * An empty name "" can be used.
     */
    public MigLayoutBuilder sizeGroupY(String groupName) {
        addComponentConstraint("sgy " + groupName);
        return this;
    }

    /**
     * Gives the component an end group name and association.<br>
     * All components that share an end group name will get their right/bottom component side aligned.<br>
     * The right/bottom side will be that of the largest component in the group.<br>
     * If "eg" or "endgroup" is used and thus the dimension is not specified the current flow dimension will be used (see "flowx").<br>
     * So "endGroup" will be the same as "endGroupX" in the normal case.<br>
     * An empty name "" can be used.
     */
    public MigLayoutBuilder endGroup(String groupName) {
        addComponentConstraint("eg " + groupName);
        return this;
    }

    /**
     * Gives the component an end group name and association.<br>
     * All components that share an end group name will get their right/bottom component side aligned.<br>
     * The right/bottom side will be that of the largest component in the group.<br>
     * If "eg" or "endgroup" is used and thus the dimension is not specified the current flow dimension will be used (see "flowx").<br>
     * So "endGroup" will be the same as "endGroupX" in the normal case.<br>
     * An empty name "" can be used.
     */
    public MigLayoutBuilder endGroupX(String groupName) {
        addComponentConstraint("egx " + groupName);
        return this;
    }

    /**
     * Gives the component an end group name and association.<br>
     * All components that share an end group name will get their right/bottom component side aligned.<br>
     * The right/bottom side will be that of the largest component in the group.<br>
     * If "eg" or "endgroup" is used and thus the dimension is not specified the current flow dimension will be used (see "flowx").<br>
     * So "endGroup" will be the same as "endGroupX" in the normal case.<br>
     * An empty name "" can be used.
     */
    public MigLayoutBuilder endGroupY(String groupName) {
        addComponentConstraint("egy " + groupName);
        return this;
    }

    /**
     * Specifies the gap between the components in the cell or to the cell edge depending on what is around this component.<br>
     * If a gap size is missing it is interpreted as 0px.
     */
    public MigLayoutBuilder gap(float left) {
        addComponentConstraint("gap " + floatToUnit(left));
        return this;
    }

    /**
     * Specifies the gap between the components in the cell or to the cell edge depending on what is around this component.<br>
     * If a gap size is missing it is interpreted as 0px.
     */
    public MigLayoutBuilder gap(String leftExpression) {
        addComponentConstraint("gap " + leftExpression);
        return this;
    }

    /**
     * Specifies the gap between the components in the cell or to the cell edge depending on what is around this component.<br>
     * If a gap size is missing it is interpreted as 0px.
     */
    public MigLayoutBuilder gap(float left, float right) {
        addComponentConstraint("gap " + floatToUnit(left) + " " + floatToUnit(right));
        return this;
    }

    /**
     * Specifies the gap between the components in the cell or to the cell edge depending on what is around this component.<br>
     * If a gap size is missing it is interpreted as 0px.
     */
    public MigLayoutBuilder gap(String left, String right) {
        addComponentConstraint("gap " + left + " " + right);
        return this;
    }

    /**
     * Specifies the gap between the components in the cell or to the cell edge depending on what is around this component.<br>
     * If a gap size is missing it is interpreted as 0px.
     */
    public MigLayoutBuilder gap(String left, String right, String top) {
        addComponentConstraint("gap " + left + " " + right + " " + top);
        return this;
    }

    /**
     * Specifies the gap between the components in the cell or to the cell edge depending on what is around this component.<br>
     * If a gap size is missing it is interpreted as 0px.
     */
    public MigLayoutBuilder gap(String left, String right, String top, String bottom) {
        addComponentConstraint("gap " + left + " " + right + " " + top + " " + bottom);
        return this;
    }

    /**
     * Positions the component with absolute coordinates relative to the container.<br>
     * If this keyword is used the component will not be put in a grid cell and will thus not affect the flow in the grid.<br>
     * One of eitherx/x2 and one ofy/y2 must not be null.<br>
     * The coordinate that is set to null will be placed so that the component get its preferred size in that dimension.<br>
     * Non-specified values will be set to null, so for instance "abs 50% 50%" is the same as "abs 50% 50% null null".<br>
     * If the position and size can be determined without references to the parent containers size it will affect the preferred size of the container.
     */
    public MigLayoutBuilder pos(float x, float y) {
        addComponentConstraint("pos " + floatToUnit(x) + " " + floatToUnit(y));
        return this;
    }

    /**
     * Positions the component with absolute coordinates relative to the container.<br>
     * If this keyword is used the component will not be put in a grid cell and will thus not affect the flow in the grid.<br>
     * One of eitherx/x2 and one ofy/y2 must not be null.<br>
     * The coordinate that is set to null will be placed so that the component get its preferred size in that dimension.<br>
     * Non-specified values will be set to null, so for instance "abs 50% 50%" is the same as "abs 50% 50% null null".<br>
     * If the position and size can be determined without references to the parent containers size it will affect the preferred size of the container.
     */
    public MigLayoutBuilder pos(float x, float y, float x2, float y2) {
        addComponentConstraint("pos " + floatToUnit(x) + " " + floatToUnit(y) + " " + floatToUnit(x2) + " " + floatToUnit(y2));
        return this;
    }

    /**
     * Positions the component with absolute coordinates relative to the container.<br>
     * If this keyword is used the component will not be put in a grid cell and will thus not affect the flow in the grid.<br>
     * One of eitherx/x2 and one ofy/y2 must not be null.<br>
     * The coordinate that is set to null will be placed so that the component get its preferred size in that dimension.<br>
     * Non-specified values will be set to null, so for instance "abs 50% 50%" is the same as "abs 50% 50% null null".<br>
     * If the position and size can be determined without references to the parent containers size it will affect the preferred size of the container.<br>
     * Absolute positions can also links to other components' bounds using their ids or groupIds. It can even use expressions around these links.<br>
     * E.g. "pos (butt.x+indent) butt1.y2" will position the component directly under the component with id "butt1", indented slightly to the right.<br>
     * There are two special bounds that are always set. "container" are set to the bounds if the container and "visual" are set to the bounds of the container minus the specified insets.<br>
     * The coordinates that can be used for these links are:<br>
     * <br>
     * .x or .y - The top left coordinate of the referenced component's bounds<br>
     * .x2 or .y2 - The lower right coordinate of the referenced component's bounds<br>
     * .w or .h - The current width and height of the referenced component.<br>
     * .xpos or .ypos - The top left coordinate of the referenced component in screen coordinates.<br>
     */
    public MigLayoutBuilder pos(String x, String y) {
        addComponentConstraint("pos " + x + " " + y);
        return this;
    }

    /**
     * Positions the component with absolute coordinates relative to the container.<br>
     * If this keyword is used the component will not be put in a grid cell and will thus not affect the flow in the grid.<br>
     * One of eitherx/x2 and one ofy/y2 must not be null.<br>
     * The coordinate that is set to null will be placed so that the component get its preferred size in that dimension.<br>
     * Non-specified values will be set to null, so for instance "abs 50% 50%" is the same as "abs 50% 50% null null".<br>
     * If the position and size can be determined without references to the parent containers size it will affect the preferred size of the container.<br>
     * Absolute positions can also links to other components' bounds using their ids or groupIds. It can even use expressions around these links.<br>
     * E.g. "pos (butt.x+indent) butt1.y2" will position the component directly under the component with id "butt1", indented slightly to the right.<br>
     * There are two special bounds that are always set. "container" are set to the bounds if the container and "visual" are set to the bounds of the container minus the specified insets.<br>
     * The coordinates that can be used for these links are:<br>
     * <br>
     * .x or .y - The top left coordinate of the referenced component's bounds<br>
     * .x2 or .y2 - The lower right coordinate of the referenced component's bounds<br>
     * .w or .h - The current width and height of the referenced component.<br>
     * .xpos or .ypos - The top left coordinate of the referenced component in screen coordinates.<br>
     */
    public MigLayoutBuilder pos(String x, String y, String x2, String y2) {
        addComponentConstraint("pos " + x + " " + y + " " + x2 + " " + y2);
        return this;
    }

    /**
     * Sets the id (or name) for the component.<br>
     * The id should be unique within a layout.<br>
     * This value will give the component a way to be referenced from other components.<br>
     * The value will be converted to lower case and are thus not case sensitive.<br>
     */
    public MigLayoutBuilder id(String id) {
        id = id.trim().toLowerCase();
        if (id.matches(".*[\\s.].*")) {
            throw new RuntimeException("Id cannot contain spaces or dots [" + id + "]");
        }
        if (ids.contains(id)) {
            throw new RuntimeException("Component with id [" + id + "] already exists. Please choose another id");
        }
        addComponentConstraint("id " + id);
        return this;
    }

    /**
     * Sets the id (or name) for the component.<br>
     * This value will give the component a way to be referenced from other components.<br>
     * Two or more components may share the group id but the id should be unique within a layout.<br>
     * The value will be converted to lower case and are thus not case sensitive.<br>
     * There must not be a dot first or last in the value string.<br>
     */
    public MigLayoutBuilder id(String groupName, String id) {
        id = id.trim().toLowerCase();
        groupName = groupName.trim();
        if (id.matches(".*[\\s.].*")) {
            throw new RuntimeException("Id cannot contain spaces or dots [" + id + "]");
        }
        if (groupName.matches(".*[\\s.].*")) {
            throw new RuntimeException("groupName cannot contain spaces or dots [" + groupName + "]");
        }
        if (ids.contains(id)) {
            throw new RuntimeException("Component with id [" + id + "] already exists. Please choose another id");
        }
        addComponentConstraint("id " + groupName + "." + id);
        ids.add(id);
        return this;
    }

    /**
     * Used to position the start (x or y), end (x2 or y2) or both edges of a component in absolute coordinates.<br>
     * This is used for when a component is in a grid or dock and it for instance needs to be adjusted to align with something else or in some other way be positioned absolutely.<br>
     * The cell that the component is positioned in will not change size, neither will the grid.<br>
     * The x, y, x2 and y2 keywords are applied in the last stage and will therefore not affect other components in the grid or dock, unless they are explicitly linked to the bounds of the component.<br>
     * If the position and size can be determined without references to the parent containers size it will affect the preferred size of the container.
     */
    public MigLayoutBuilder x(float value) {
        addComponentConstraint("x " + floatToUnit(value));
        return this;
    }

    /**
     * Used to position the start (x or y), end (x2 or y2) or both edges of a component in absolute coordinates.<br>
     * This is used for when a component is in a grid or dock and it for instance needs to be adjusted to align with something else or in some other way be positioned absolutely.<br>
     * The cell that the component is positioned in will not change size, neither will the grid.<br>
     * The x, y, x2 and y2 keywords are applied in the last stage and will therefore not affect other components in the grid or dock, unless they are explicitly linked to the bounds of the component.<br>
     * If the position and size can be determined without references to the parent containers size it will affect the preferred size of the container.
     */
    public MigLayoutBuilder x(String value) {
        addComponentConstraint("x " + value);
        return this;
    }

    /**
     * Used to position the start (x or y), end (x2 or y2) or both edges of a component in absolute coordinates.<br>
     * This is used for when a component is in a grid or dock and it for instance needs to be adjusted to align with something else or in some other way be positioned absolutely.<br>
     * The cell that the component is positioned in will not change size, neither will the grid.<br>
     * The x, y, x2 and y2 keywords are applied in the last stage and will therefore not affect other components in the grid or dock, unless they are explicitly linked to the bounds of the component.<br>
     * If the position and size can be determined without references to the parent containers size it will affect the preferred size of the container.
     */
    public MigLayoutBuilder x2(float value) {
        addComponentConstraint("x2 " + floatToUnit(value));
        return this;
    }

    /**
     * Used to position the start (x or y), end (x2 or y2) or both edges of a component in absolute coordinates.<br>
     * This is used for when a component is in a grid or dock and it for instance needs to be adjusted to align with something else or in some other way be positioned absolutely.<br>
     * The cell that the component is positioned in will not change size, neither will the grid.<br>
     * The x, y, x2 and y2 keywords are applied in the last stage and will therefore not affect other components in the grid or dock, unless they are explicitly linked to the bounds of the component.<br>
     * If the position and size can be determined without references to the parent containers size it will affect the preferred size of the container.
     */
    public MigLayoutBuilder x2(String value) {
        addComponentConstraint("x2 " + value);
        return this;
    }

    /**
     * Used to position the start (x or y), end (x2 or y2) or both edges of a component in absolute coordinates.<br>
     * This is used for when a component is in a grid or dock and it for instance needs to be adjusted to align with something else or in some other way be positioned absolutely.<br>
     * The cell that the component is positioned in will not change size, neither will the grid.<br>
     * The x, y, x2 and y2 keywords are applied in the last stage and will therefore not affect other components in the grid or dock, unless they are explicitly linked to the bounds of the component.<br>
     * If the position and size can be determined without references to the parent containers size it will affect the preferred size of the container.
     */
    public MigLayoutBuilder y(float value) {
        addComponentConstraint("y " + floatToUnit(value));
        return this;
    }

    /**
     * Used to position the start (x or y), end (x2 or y2) or both edges of a component in absolute coordinates.<br>
     * This is used for when a component is in a grid or dock and it for instance needs to be adjusted to align with something else or in some other way be positioned absolutely.<br>
     * The cell that the component is positioned in will not change size, neither will the grid.<br>
     * The x, y, x2 and y2 keywords are applied in the last stage and will therefore not affect other components in the grid or dock, unless they are explicitly linked to the bounds of the component.<br>
     * If the position and size can be determined without references to the parent containers size it will affect the preferred size of the container.
     */
    public MigLayoutBuilder y(String value) {
        addComponentConstraint("y " + value);
        return this;
    }

    /**
     * Used to position the start (x or y), end (x2 or y2) or both edges of a component in absolute coordinates.<br>
     * This is used for when a component is in a grid or dock and it for instance needs to be adjusted to align with something else or in some other way be positioned absolutely.<br>
     * The cell that the component is positioned in will not change size, neither will the grid.<br>
     * The x, y, x2 and y2 keywords are applied in the last stage and will therefore not affect other components in the grid or dock, unless they are explicitly linked to the bounds of the component.<br>
     * If the position and size can be determined without references to the parent containers size it will affect the preferred size of the container.
     */
    public MigLayoutBuilder y2(float value) {
        addComponentConstraint("y2 " + floatToUnit(value));
        return this;
    }

    /**
     * Used to position the start (x or y), end (x2 or y2) or both edges of a component in absolute coordinates.<br>
     * This is used for when a component is in a grid or dock and it for instance needs to be adjusted to align with something else or in some other way be positioned absolutely.<br>
     * The cell that the component is positioned in will not change size, neither will the grid.<br>
     * The x, y, x2 and y2 keywords are applied in the last stage and will therefore not affect other components in the grid or dock, unless they are explicitly linked to the bounds of the component.<br>
     * If the position and size can be determined without references to the parent containers size it will affect the preferred size of the container.
     */
    public MigLayoutBuilder y2(String value) {
        addComponentConstraint("y2 " + value);
        return this;
    }

    /**
     * Used for docking the component at an edge, or the center, of the container.<br>
     * Works much like BorderLayout except that there can be an arbitrary number of docking components.<br>
     * They get the docked space in the order they are added to the container and "cuts that piece of".<br>
     * The "dock" keyword can be omitted for all but "center" and is only there to use for clarity.<br>
     * The component will be put in special surrounding cells that spans the rest of the rows which means that the docking constraint can be combined with many other constraints such as padding, width, height and gap.
     */
    public MigLayoutBuilder dock(MigDock dock) {
        addComponentConstraint("dock " + dock.getValue());
        return this;
    }

    /**
     * Sets the padding for the component in absolute pixels.<br>
     * This is an absolute adjustment of the bounds if the component and is done at the last stage in the layout process.<br>
     * This means it will not affect gaps or cell size or move other components. It can be used to compensate for something that for some reason is hard to do with the other constraints.<br>
     * For instance "ins -5 -5 5 5" will enlarge the component five pixels in all directions making it 10 pixels taller and wider.<br>
     * If values are omitted they will be set to 0.<br>
     * <b>Note!</b> Padding multi-line components derived from JTextComponent (such as JTextArea) without setting a explicit minimum size may result in an continuous size escalation (animated!).<br>
     * This is not a bug in the layout manager but a "feature" derived from how these components calculates their minimum size.<br>
     * If the size is padded so that it increases by one pixel, the text component will automatically issue a revalidation and the layout cycle will restart, now with a the newly increased size as the new minimum size.<br>
     * This will continue until the maximum size is reached. This only happens for components that have "line wrap" set to true.
     */
    public MigLayoutBuilder pad(int top, int left) {
        addComponentConstraint("pad " + top + " " + left);
        return this;
    }

    /**
     * Sets the padding for the component in absolute pixels.<br>
     * This is an absolute adjustment of the bounds if the component and is done at the last stage in the layout process.<br>
     * This means it will not affect gaps or cell size or move other components. It can be used to compensate for something that for some reason is hard to do with the other constraints.<br>
     * For instance "ins -5 -5 5 5" will enlarge the component five pixels in all directions making it 10 pixels taller and wider.<br>
     * If values are omitted they will be set to 0.<br>
     * <b>Note!</b> Padding multi-line components derived from JTextComponent (such as JTextArea) without setting a explicit minimum size may result in an continuous size escalation (animated!).<br>
     * This is not a bug in the layout manager but a "feature" derived from how these components calculates their minimum size.<br>
     * If the size is padded so that it increases by one pixel, the text component will automatically issue a revalidation and the layout cycle will restart, now with a the newly increased size as the new minimum size.<br>
     * This will continue until the maximum size is reached. This only happens for components that have "line wrap" set to true.
     */
    public MigLayoutBuilder pad(int top, int left, int bottom, int right) {
        addComponentConstraint("pad " + top + " " + left + " " + bottom + " " + right);
        return this;
    }

    /**
     * Specifies the alignment for the component if the cell is larger than the component plus its gaps.<br>
     * In a cell where there is more than one component, the first component can set the alignment for all the components.<br>
     * It is not possible to for instance set the first component to be left aligned and the second to be right aligned and thus get a gap between them.<br>
     * That effect can better be accomplished by setting a gap between the components that have a minimum size and a large preferred size.
     */
    public MigLayoutBuilder alignX(MigAlign align) {
        addComponentConstraint("alignx " + align.getValue());
        return this;
    }

    /**
     * Specifies the alignment for the component if the cell is larger than the component plus its gaps.<br>
     * In a cell where there is more than one component, the first component can set the alignment for all the components.<br>
     * It is not possible to for instance set the first component to be left aligned and the second to be right aligned and thus get a gap between them.<br>
     * That effect can better be accomplished by setting a gap between the components that have a minimum size and a large preferred size.
     */
    public MigLayoutBuilder alignY(MigAlign align) {
        addComponentConstraint("aligny " + align.getValue());
        return this;
    }

    /**
     * Specifies the alignment for the component if the cell is larger than the component plus its gaps.<br>
     * In a cell where there is more than one component, the first component can set the alignment for all the components.<br>
     * It is not possible to for instance set the first component to be left aligned and the second to be right aligned and thus get a gap between them.<br>
     * That effect can better be accomplished by setting a gap between the components that have a minimum size and a large preferred size.
     */
    public MigLayoutBuilder alignX(String alignExpression) {
        addComponentConstraint("alignx " + alignExpression);
        return this;
    }

    /**
     * Specifies the alignment for the component if the cell is larger than the component plus its gaps.<br>
     * In a cell where there is more than one component, the first component can set the alignment for all the components.<br>
     * It is not possible to for instance set the first component to be left aligned and the second to be right aligned and thus get a gap between them.<br>
     * That effect can better be accomplished by setting a gap between the components that have a minimum size and a large preferred size.
     */
    public MigLayoutBuilder alignY(String alignExpression) {
        addComponentConstraint("aligny " + alignExpression);
        return this;
    }

    /**
     * Prevents MigLayout to change the bounds for the component.<br>
     * The bounds should be handled/set from code outside this layout manager by calling the setBounds(..) (or equivalent depending on the GUI toolkit used) directly on the component.<br>
     * This component's bounds can still be linked to by other components if it has an "id" tag, or a link id is provided by the ComponentWrapper.<br>
     * This is a very simple and powerful way to extend the usages for MigLayout and reduce the number of times a custom layout manager has to be written.<br>
     * Normal application code can be used to set the bounds, something that can't be done with any other layout managers.
     */
    public MigLayoutBuilder external() {
        addComponentConstraint("external");
        return this;
    }

    /**
     * Sets the hide mode for the component. The hide mode specified how the layout manager should handle a component that isn't visible.<br>
     * If the hide mode has been specified in the layout, this hide mode can be override it.
     */
    public MigLayoutBuilder hideMode(MigHideMode mode) {
        addComponentConstraint("hidemode " + mode.getOrdinal());
        return this;
    }

    public MigLayoutBuilder tag(MigTag tag) {
        addComponentConstraint("tag " + tag.getTag());
        return this;
    }


    @SuppressWarnings("SameParameterValue")
    private String joinStrings(String delimiter, String... values) {
        StringJoiner sj = new StringJoiner(delimiter);
        for (String value : values) {
            sj.add(value);
        }
        return sj.toString();
    }

    private String floatsToUnit(float... values) {
        StringBuilder sb = new StringBuilder();
        for (float value : values) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(floatToUnit(value));
        }
        return sb.toString();
    }

    private String floatToUnit(float v) {
        switch (defaultUnits) {
            case Pixels:
                return v + "px";
            case Percent:
                return v + "%";
            case LogicalPixels:
                return v + "lp";
            case Points:
                return v + "pt";
            case Centimeters:
                return v + "cm";
            case Millimeters:
                return v + "mm";
            case Inches:
                return v + "in";
            case ScreenPercentage:
                return "sp " + v;
            case VisualBoundsAlignment:
                return v + "al";
            default:
                throw new IllegalArgumentException("Unknown unit " + defaultUnits);
        }
    }

    private void addLayoutConstraint(String property) {
        if (layoutConstraints.length() > 0) {
            layoutConstraints.append(",");
        }
        layoutConstraints.append(property);
    }

    private void addComponentConstraint(String property) {
        if (componentConstraints.length() > 0) {
            componentConstraints.append(",");
        }
        componentConstraints.append(property);
    }
}
