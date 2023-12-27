package com.swinglayoutbuilder.mig;

import java.util.ArrayList;
import java.util.List;

public class MigRowColumnConfigBuilder {
    private final List<String> configs = new ArrayList<>();
    private StringBuilder current = new StringBuilder();

    public MigRowColumnConfigBuilder newRow() {
        String currentConfig = current.toString().trim();
        current = new StringBuilder();
        if (!currentConfig.isEmpty()) {
            configs.add(currentConfig);
        }
        return this;
    }

    public MigRowColumnConfigBuilder newColumn() {
        newRow();
        return this;
    }

    /**
     * Puts the row in flow-only mode.<br>
     * All components in the flow direction will be put in the same cell and will thus not be aligned with component in other rows/columns.<br>
     * This property will only be adhered to if the row is in the flow direction.<br>
     * So for the normal horizontal flow ("flowx") it is only used for rows and for "flowy" it is only used for columns.
     */
    public MigRowColumnConfigBuilder noGrid() {
        addConstraint("nogrid");
        return this;
    }

    /**
     * Sets how keen the row should be to grow in relation to other rows.<br>
     * The weight (defaults to 100 if not specified) is purely a relative value to other rows' weight.<br>
     * Twice the weight will get double the extra space.<br>
     * If this constraint is not set, the grow weight is set to zero and the column will not grow (unless "fill" is set in the Layout Constraints and no other row has grow weight above zero either).<br>
     * Grow weight will only be compared to the weights for rows with the same grow priority. See below.
     */
    public MigRowColumnConfigBuilder grow() {
        addConstraint("grow");
        return this;
    }

    /**
     * Sets how keen the row should be to grow in relation to other rows.<br>
     * The weight (defaults to 100 if not specified) is purely a relative value to other rows' weight.<br>
     * Twice the weight will get double the extra space.<br>
     * If this constraint is not set, the grow weight is set to zero and the column will not grow (unless "fill" is set in the Layout Constraints and no other row has grow weight above zero either).<br>
     * Grow weight will only be compared to the weights for rows with the same grow priority. See below.
     */
    public MigRowColumnConfigBuilder grow(int weight) {
        addConstraint("grow " + weight);
        return this;
    }

    /**
     * Sets the grow priority for the row (not for the components in the row).<br>
     * When growing, all rows with higher priorities will be grown to their maximum size before any row with lower priority are considered.<br>
     * The default grow priority is 100. This can be used to make certain rows grow to max before other rows even start to grow.
     */
    public MigRowColumnConfigBuilder growPriority(int priority) {
        addConstraint("growprio " + priority);
        return this;
    }

    /**
     * Sets how keen/reluctant the row should be to shrink in relation to other rows.<br>
     * The weight is purely a relative value to other rows' weights.<br>
     * Twice the weight will shrink twice as much when space is scarce.<br>
     * If this constraint is not set the shrink weight defaults to 100, which means that all rows by default can shrink to their minimum size, but no less.<br>
     * Shrink weight will only be compared against the weights in the same shrink priority group (other rows with the same shrink priority).
     */
    public MigRowColumnConfigBuilder shrink(int weight) {
        addConstraint("shrink " + weight);
        return this;
    }

    /**
     * Sets the shrink priority for the row (not for the components in the row).<br>
     * When space is scarce and rows needs to be shrunk, all rows with higher priorities will be shrunk to their minimum size before any row with lower priority are considered.<br>
     * The default shrink priority is 100.<br>
     * This can be used to make certain rows shrink to min before other rows even start to shrink.
     */
    public MigRowColumnConfigBuilder shrinkPriority(int priority) {
        addConstraint("shrinkprio " + priority);
        return this;
    }

    /**
     * Specifies the default alignment for the components in the row.<br>
     * This default alignment can be overridden by setting the alignment for the component in the Component Constraint.<br>
     * The default row alignment is "left" for columns and "center" for rows.
     */
    public MigRowColumnConfigBuilder align(MigAlign align) {
        addConstraint("al " + align.getValue());
        return this;
    }

    /**
     * Specifies the default alignment for the components in the row.<br>
     * This default alignment can be overridden by setting the alignment for the component in the Component Constraint.<br>
     * The default row alignment is "left" for columns and "center" for rows.
     */
    public MigRowColumnConfigBuilder align(String alignExpression) {
        addConstraint("al " + alignExpression);
        return this;
    }

    /**
     * Set the default value for components to "grow" in the dimension of the row.<br>
     * So for columns the components in that column will default to a "growx" constraint (which can be overridden by the individual component constraints).<br>
     * Note that this property does not affect the size for the row, but rather the size of the components in the row.
     */
    public MigRowColumnConfigBuilder fill() {
        addConstraint("fill");
        return this;
    }

    /**
     * Gives the row a size group name.
     * All rows that share a size group name will get the same size as the row with the largest min/preferred size.
     * This is most usable when the size of the row is not explicitly set and thus is determined by the largest component is the row(s).
     */
    public MigRowColumnConfigBuilder sizeGroup(String groupName) {
        groupName = groupName.trim();
        if (groupName.matches(".*[\\s.].*")) {
            throw new RuntimeException("groupName cannot contain spaces or dots [" + groupName + "]");
        }

        addConstraint("sg " + groupName);
        return this;
    }

    public String finish() {
        newRow();
        StringBuilder sb = new StringBuilder();
        for (String config : configs) {
            if (!config.isEmpty()) {
                sb.append("[").append(config).append("]");
            }
        }
        return sb.toString();
    }

    private void addConstraint(String property) {
        if (current.length() > 0) {
            current.append(",");
        }
        current.append(property);
    }
}
