package com.mindease.service;

import com.mindease.dao.MoodEntryDAO;
import com.mindease.dao.MoodTagDAO;
import com.mindease.model.MoodEntry;
import com.mindease.model.MoodTag;
import com.mindease.model.WeeklyMood;

import java.util.List;
import java.util.Map;

/**
 * Provides operations for mood entries and tags.
 */
public class MoodEntryService {
    private final MoodEntryDAO moodEntryDAO = new MoodEntryDAO();
    private final MoodTagDAO moodTagDAO = new MoodTagDAO();

    /**
     * Returns the total number of mood entries for a user.
     *
     * @param userId the user id
     * @return the total entry count
     */
    public int getTotalCountByUserId(int userId) {
        return moodEntryDAO.getTotalCountByUserId(userId);
    }

    /**
     * Returns the average mood score for a user.
     *
     * @param userId the user id
     * @return the average score
     */
    public double getAverageScoreByUserId(int userId) {
        return moodEntryDAO.getAverageScoreByUserId(userId);
    }

    /**
     * Returns the number of positive days for a user.
     *
     * @param userId the user id
     * @return the positive day count
     */
    public int getPositiveDaysCount(int userId) {
        return moodEntryDAO.getPositiveDaysCount(userId);
    }

    /**
     * Returns the best day label for a user.
     *
     * @param userId the user id
     * @return the best day string or null when not available
     */
    public String getBestDayByUserId(int userId) {
        return moodEntryDAO.getBestDayByUserId(userId);
    }

    /**
     * Retrieves recent mood entries for a user.
     *
     * @param userId the user id
     * @param limit the maximum number of entries to return
     * @return the list of recent entries
     */
    public List<MoodEntry> getRecentByUserId(int userId, int limit) {
        return moodEntryDAO.getRecentByUserId(userId, limit);
    }

    /**
     * Retrieves all mood entries for a user.
     *
     * @param userId the user id
     * @return the list of entries
     */
    public List<MoodEntry> getAllByUserId(int userId) {
        return moodEntryDAO.getAllByUserId(userId);
    }

    /**
     * Retrieves today's mood entry for a user.
     *
     * @param userId the user id
     * @return today's entry or null when none exists
     */
    public MoodEntry getTodayEntry(int userId) {
        return moodEntryDAO.getTodayEntry(userId);
    }

    /**
     * Retrieves today's mood entry for a user (summary).
     *
     * @param userId the user id
     * @return today's mood entry or null when none exists
     */
    public MoodEntry getTodayMood(int userId) {
        return moodEntryDAO.getTodayMood(userId);
    }

    /**
     * Checks whether a user has already logged a mood entry today.
     *
     * @param userId the user id
     * @return true when an entry exists; false otherwise
     */
    public boolean hasTodayEntry(int userId) {
        return moodEntryDAO.hasTodayEntry(userId);
    }

    /**
     * Inserts a new mood entry.
     *
     * @param entry the mood entry to insert
     * @return the new entry id
     */
    public int insert(MoodEntry entry) {
        return moodEntryDAO.insert(entry);
    }

    /**
     * Updates an existing mood entry.
     *
     * @param entry the mood entry to update
     * @return the number of rows affected
     */
    public int update(MoodEntry entry) {
        return moodEntryDAO.update(entry);
    }

    /**
     * Retrieves all mood tags.
     *
     * @return the list of tags
     */
    public List<MoodTag> getAllTags() {
        return moodTagDAO.getAllTags();
    }

    /**
     * Replaces tags for a mood entry.
     *
     * @param entryId the entry id
     * @param tagIds the tag ids to associate
     */
    public void replaceEntryTags(int entryId, List<Integer> tagIds) {
        moodTagDAO.deleteEntryTags(entryId);
        moodTagDAO.saveEntryTags(entryId, tagIds);
    }

    /**
     * Returns the current mood streak for a user.
     *
     * @param userId the user id
     * @return the mood streak length
     */
    public int getMoodStreak(int userId) {
        return moodEntryDAO.getMoodStreak(userId);
    }

    /**
     * Returns the total number of mood logs for a user.
     *
     * @param userId the user id
     * @return the total log count
     */
    public int getTotalLogs(int userId) {
        return moodEntryDAO.getTotalCountByUserId(userId);
    }

    /**
     * Returns the average mood score for a user.
     *
     * @param userId the user id
     * @return the average mood score
     */
    public double getAvgMoodScore(int userId) {
        return moodEntryDAO.getAverageScoreByUserId(userId);
    }

    /**
     * Retrieves weekly mood data for a user.
     *
     * @param userId the user id
     * @return the weekly mood data
     */
    public List<WeeklyMood> getWeeklyMoodData(int userId) {
        return moodEntryDAO.getWeeklyMoodData(userId);
    }

    /**
     * Returns the number of mood entries created today.
     *
     * @return today's mood entry count
     */
    public int getTodayMoodEntriesCount() {
        return moodEntryDAO.getTodayMoodEntriesCount();
    }

    /**
     * Returns the total number of mood entries.
     *
     * @return the total mood entry count
     */
    public int getTotalMoodEntriesCount() {
        return moodEntryDAO.getTotalMoodEntriesCount();
    }

    /**
     * Returns the average mood score across all users.
     *
     * @return the average mood score
     */
    public double getAverageMoodScore() {
        return moodEntryDAO.getAverageMoodScore();
    }

    /**
     * Returns the mood distribution map.
     *
     * @return the mood score distribution
     */
    public Map<Integer, Integer> getMoodDistribution() {
        return moodEntryDAO.getMoodDistribution();
    }
}