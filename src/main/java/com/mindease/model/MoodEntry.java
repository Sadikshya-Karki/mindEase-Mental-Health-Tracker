package com.mindease.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user's daily mood log entry.
 */
public class MoodEntry {

    private int entryId;
    private int userId;
    private int moodScore;
    private String note;
    private Date entryDate;
    private Timestamp createdAt;
    private List<String> tags = new ArrayList<>();

    /**
     * Creates an empty mood entry instance.
     */
    public MoodEntry() {}

    /**
     * Gets the entry id.
     *
     * @return entry id
     */
    public int getEntryId() { return entryId; }

    /**
     * Sets the entry id.
     *
     * @param entryId entry id
     */
    public void setEntryId(int entryId) { this.entryId = entryId; }

    /**
     * Gets the user id for this entry.
     *
     * @return user id
     */
    public int getUserId() { return userId; }

    /**
     * Sets the user id for this entry.
     *
     * @param userId user id
     */
    public void setUserId(int userId) { this.userId = userId; }

    /**
     * Gets the mood score.
     *
     * @return mood score
     */
    public int getMoodScore() { return moodScore; }

    /**
     * Sets the mood score.
     *
     * @param score mood score
     */
    public void setScore(int score) { this.moodScore = score; }

    /**
     * Gets the mood score.
     *
     * @return mood score
     */
    public int getScore() { return moodScore; }

    /**
     * Gets the optional mood note.
     *
     * @return mood note
     */
    public String getNote() { return note; }

    /**
     * Sets the optional mood note.
     *
     * @param note mood note
     */
    public void setNote(String note) { this.note = note; }

    /**
     * Gets the entry date.
     *
     * @return entry date
     */
    public Date getEntryDate() { return entryDate; }

    /**
     * Sets the entry date.
     *
     * @param entryDate entry date
     */
    public void setEntryDate(Date entryDate) { this.entryDate = entryDate; }

    /**
     * Gets the creation timestamp.
     *
     * @return creation timestamp
     */
    public Timestamp getCreatedAt() { return createdAt; }

    /**
     * Sets the creation timestamp.
     *
     * @param createdAt creation timestamp
     */
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    /**
     * Gets mood tags associated with this entry.
     *
     * @return mood tags
     */
    public List<String> getTags() { return tags; }

    /**
     * Sets mood tags associated with this entry.
     *
     * @param tags mood tags
     */
    public void setTags(List<String> tags) { this.tags = tags; }
}
