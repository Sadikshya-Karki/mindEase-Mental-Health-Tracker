package com.mindease.model;

/**
 * Represents an educational or wellness resource available in the platform.
 */
public class Resource {

    private int resourceId;
    private String title;
    private String description;
    private int categoryId;
    private String url;
    private int addedBy;
    private String addedByName;
    private String authorName;
    private String status;
    private String categoryName;
    private String readTime;
    private String imageUrl;
    private String tags;
    private String colorCode;

    /**
     * Creates an empty resource instance.
     */
    public Resource() {}

    /**
     * Gets the resource id.
     *
     * @return resource id
     */
    public int getResourceId() { return resourceId; }

    /**
     * Sets the resource id.
     *
     * @param resourceId resource id
     */
    public void setResourceId(int resourceId) { this.resourceId = resourceId; }

    /**
     * Gets the resource title.
     *
     * @return title
     */
    public String getTitle() { return title; }

    /**
     * Sets the resource title.
     *
     * @param title title
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Gets the resource description.
     *
     * @return description
     */
    public String getDescription() { return description; }

    /**
     * Sets the resource description.
     *
     * @param description description
     */
    public void setDescription(String description) { this.description = description; }

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
     * Gets the resource URL.
     *
     * @return resource URL
     */
    public String getUrl() { return url; }

    /**
     * Sets the resource URL.
     *
     * @param url resource URL
     */
    public void setUrl(String url) { this.url = url; }

    /**
     * Gets the id of the user who added the resource.
     *
     * @return user id
     */
    public int getAddedBy() { return addedBy; }

    /**
     * Sets the id of the user who added the resource.
     *
     * @param addedBy user id
     */
    public void setAddedBy(int addedBy) { this.addedBy = addedBy; }



    /**
     * Gets the author name alias.
     *
     * @return author name
     */
    public String getAuthorName() { return authorName; }

    /**
     * Sets the author name alias.
     * Updates addedByName alias as well.
     *
     * @param authorName author name
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
        this.addedByName = authorName;
    }

    /**
     * Gets publication status.
     *
     * @return status
     */
    public String getStatus() { return status; }

    /**
     * Sets publication status.
     *
     * @param status status
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * Gets category name for display.
     *
     * @return category name
     */
    public String getCategoryName() { return categoryName; }

    /**
     * Sets category name for display.
     *
     * @param categoryName category name
     */
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    /**
     * Gets estimated read time text.
     *
     * @return read time
     */
    public String getReadTime() { return readTime; }

    /**
     * Sets estimated read time text.
     *
     * @param readTime read time
     */
    public void setReadTime(String readTime) { this.readTime = readTime; }

    /**
     * Gets the image URL.
     *
     * @return image URL
     */
    public String getImageUrl() { return imageUrl; }

    /**
     * Sets the image URL.
     *
     * @param imageUrl image URL
     */
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    /**
     * Gets tags associated with the resource.
     *
     * @return tag string
     */
    public String getTags() { return tags; }

    /**
     * Sets tags associated with the resource.
     *
     * @param tags tag string
     */
    public void setTags(String tags) { this.tags = tags; }

    /**
     * Gets category color code for display.
     *
     * @return color code
     */
    public String getColorCode() { return colorCode; }

    /**
     * Sets category color code for display.
     *
     * @param colorCode color code
     */
    public void setColorCode(String colorCode) { this.colorCode = colorCode; }

}

