package com.mindease.model;

/**
 * Represents a predefined mood tag that can be attached to a mood entry.
 */
public class MoodTag {

    private int tagId;
    private String tagName;

    /**
     * Gets the tag id.
     *
     * @return tag id
     */
    public int getTagId() {
        return tagId;
    }

    /**
     * Sets the tag id.
     *
     * @param tagId tag id
     */
    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    /**
     * Gets the tag name.
     *
     * @return tag name
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Sets the tag name.
     *
     * @param tagName tag name
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
