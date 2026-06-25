package com.mindease.model;

import java.sql.Date;

/**
 * Represents aggregated mood information for a single day in a weekly view.
 */
public class WeeklyMood {

    private Date date;
    private int moodScore;
    private String dayLabel;
    private int barHeight;
    private String moodLabel;

    /**
     * Gets the date represented by this data point.
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date represented by this data point.
     *
     * @param date date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the aggregated mood score.
     *
     * @return mood score
     */
    public int getMoodScore() {
        return moodScore;
    }

    /**
     * Sets the aggregated mood score.
     *
     * @param moodScore mood score
     */
    public void setMoodScore(int moodScore) {
        this.moodScore = moodScore;
    }

    /**
     * Gets the short day label.
     *
     * @return day label
     */
    public String getDayLabel() {
        return dayLabel;
    }

    /**
     * Sets the short day label.
     *
     * @param dayLabel day label
     */
    public void setDayLabel(String dayLabel) {
        this.dayLabel = dayLabel;
    }

    /**
     * Gets bar height used by the chart UI.
     *
     * @return bar height in pixels
     */
    public int getBarHeight() {
        return barHeight;
    }

    /**
     * Sets bar height used by the chart UI.
     *
     * @param barHeight bar height in pixels
     */
    public void setBarHeight(int barHeight) {
        this.barHeight = barHeight;
    }

    /**
     * Gets the mood label text.
     *
     * @return mood label
     */
    public String getMoodLabel() {
        return moodLabel;
    }

    /**
     * Sets the mood label text.
     *
     * @param moodLabel mood label
     */
    public void setMoodLabel(String moodLabel) {
        this.moodLabel = moodLabel;
    }
}

