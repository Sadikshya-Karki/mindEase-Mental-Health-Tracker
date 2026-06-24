package com.mindease.model;

/**
 * Represents a resource category used to organize wellness content.
 */
public class Category {

    private int categoryId;
    private String name;
    private String description;
    private String colorCode;

    /**
     * Creates an empty category instance.
     */
    public Category() {}

    /**
     * Gets the category id.
     *
     * @return category id
     */
    public int getCategoryId() { return categoryId; }

    /**
     * Sets the category id.
     *
     * @param categoryId category id
     */
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    /**
     * Gets the category name.
     *
     * @return category name
     */
    public String getName() { return name; }

    /**
     * Sets the category name.
     *
     * @param name category name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets the category description.
     *
     * @return category description
     */
    public String getDescription() { return description; }

    /**
     * Sets the category description.
     *
     * @param description category description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Gets the display color code for the category.
     *
     * @return hex color code
     */
    public String getColorCode() { return colorCode; }

    /**
     * Sets the display color code for the category.
     *
     * @param colorCode hex color code
     */
    public void setColorCode(String colorCode) { this.colorCode = colorCode; }
}

