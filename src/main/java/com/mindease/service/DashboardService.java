package com.mindease.service;

import com.mindease.model.Appointment;
import com.mindease.model.MoodEntry;
import com.mindease.model.Resource;
import com.mindease.model.User;
import com.mindease.model.WeeklyMood;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Aggregates dashboard data for users and administrators.
 */
public class DashboardService {
    private final MoodEntryService moodEntryService = new MoodEntryService();
    private final BookmarkService bookmarkService = new BookmarkService();
    private final AppointmentService appointmentService = new AppointmentService();
    private final AdminUserService adminUserService = new AdminUserService();
    private final ResourceService resourceService = new ResourceService();
    private final CounselorService counselorService = new CounselorService();

    /**
     * Returns the current mood streak for a user.
     *
     * @param userId the user id
     * @return the mood streak length
     */
    public int getMoodStreak(int userId) {
        return moodEntryService.getMoodStreak(userId);
    }

    /**
     * Returns the total number of mood logs for a user.
     *
     * @param userId the user id
     * @return the total log count
     */
    public int getTotalLogs(int userId) {
        return moodEntryService.getTotalLogs(userId);
    }

    /**
     * Returns the number of saved resources for a user.
     *
     * @param userId the user id
     * @return the saved resource count
     */
    public int getSavedResourcesCount(int userId) {
        return bookmarkService.getSavedResourcesCount(userId);
    }

    /**
     * Returns the number of appointments for a user.
     *
     * @param userId the user id
     * @return the appointment count
     */
    public int getAppointmentsCount(int userId) {
        return appointmentService.getAppointmentsCount(userId);
    }

    /**
     * Returns the average mood score for a user.
     *
     * @param userId the user id
     * @return the average mood score
     */
    public double getAvgMoodScore(int userId) {
        return moodEntryService.getAvgMoodScore(userId);
    }

    /**
     * Retrieves today's mood entry for a user.
     *
     * @param userId the user id
     * @return today's mood entry or null when none exists
     */
    public MoodEntry getTodayMood(int userId) {
        return moodEntryService.getTodayMood(userId);
    }

    /**
     * Retrieves weekly mood data for a user.
     *
     * @param userId the user id
     * @return the list of weekly mood entries
     */
    public List<WeeklyMood> getWeeklyMoodData(int userId) {
        return moodEntryService.getWeeklyMoodData(userId);
    }

    /**
     * Retrieves recent mood entries for a user.
     *
     * @param userId the user id
     * @param limit the maximum number of entries to return
     * @return the list of recent mood entries
     */
    public List<MoodEntry> getRecentMoodEntries(int userId, int limit) {
        return moodEntryService.getRecentByUserId(userId, limit);
    }

    /**
     * Builds a seven-day chart data set from weekly mood data.
     *
     * @param source the source mood data
     * @return the chart-ready weekly mood list
     */
    public List<WeeklyMood> buildSevenDayChartData(List<WeeklyMood> source) {
        Map<LocalDate, Integer> byDate = new HashMap<>();
        for (WeeklyMood mood : source) {
            if (mood.getDate() != null) {
                byDate.put(mood.getDate().toLocalDate(), mood.getMoodScore());
            }
        }

        List<WeeklyMood> chart = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(today.getDayOfWeek().getValue() % 7L);
        for (int i = 0; i < 7; i++) {
            LocalDate day = start.plusDays(i);
            int score = byDate.getOrDefault(day, 0);
            WeeklyMood item = new WeeklyMood();
            item.setDate(Date.valueOf(day));
            item.setMoodScore(score);
            item.setDayLabel(day.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            item.setBarHeight(getBarHeight(score));
            item.setMoodLabel(getMoodLabel(score));
            chart.add(item);
        }
        return chart;
    }

    /**
     * Computes the bar height for a mood score.
     *
     * @param score the mood score
     * @return the bar height value
     */
    private int getBarHeight(int score) {
        return score > 0 ? 26 + (score * 28) : 10;
    }

    /**
     * Maps a mood score to a display label.
     *
     * @param score the mood score
     * @return the display label
     */
    private String getMoodLabel(int score) {
        return switch (score) {
            case 1 -> "Very Low";
            case 2 -> "Low";
            case 3 -> "Neutral";
            case 4 -> "Good";
            case 5 -> "Great";
            default -> "No Log";
        };
    }

    /**
     * Retrieves upcoming sessions for a user.
     *
     * @param userId the user id
     * @param limit the maximum number of sessions to return
     * @return the list of upcoming sessions
     */
    public List<Appointment> getUpcomingSessions(int userId, int limit) {
        return appointmentService.getUpcomingSessions(userId, limit);
    }

    /**
     * Retrieves saved resources for a user.
     *
     * @param userId the user id
     * @param limit the maximum number of resources to return
     * @return the list of saved resources
     */
    public List<Resource> getSavedResources(int userId, int limit) {
        return bookmarkService.getSavedResources(userId, limit);
    }

    /**
     * Returns the total number of users.
     *
     * @return the total user count
     */
    public int getTotalUsers() {
        return adminUserService.getTotalUsers();
    }

    /**
     * Returns the total number of resources.
     *
     * @return the total resource count
     */
    public int getTotalResources() {
        return resourceService.getTotalResourcesCount();
    }

    /**
     * Returns the total number of counselors.
     *
     * @return the total counselor count
     */
    public int getTotalCounselors() {
        return counselorService.getActiveCounselorsCount();
    }

    /**
     * Returns the number of pending appointments.
     *
     * @return the pending appointment count
     */
    public int getPendingAppointments() {
        return appointmentService.getPendingAppointmentsCount();
    }

    /**
     * Returns the number of new registrations today.
     *
     * @return the new registration count
     */
    public int getNewRegistrations() {
        return adminUserService.getTodayRegistrationsCount();
    }

    /**
     * Returns the number of mood logs recorded today.
     *
     * @return today's mood log count
     */
    public int getMoodLogsToday() {
        return moodEntryService.getTodayMoodEntriesCount();
    }

    /**
     * Returns the number of sessions scheduled for today.
     *
     * @return today's session count
     */
    public int getSessionsToday() {
        return appointmentService.getTodayApprovedSessionsCount();
    }

    /**
     * Retrieves the most recent users.
     *
     * @param limit the maximum number of users to return
     * @return the list of recent users
     */
    public List<User> getRecentUsers(int limit) {
        return adminUserService.getRecentUsers(limit);
    }
}