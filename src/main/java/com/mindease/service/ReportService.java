package com.mindease.service;

import java.util.List;
import java.util.Map;

/**
 * Aggregates reporting metrics for administrative dashboards.
 */
public class ReportService {
    private final AdminUserService adminUserService = new AdminUserService();
    private final MoodEntryService moodEntryService = new MoodEntryService();
    private final CounselorService counselorService = new CounselorService();
    private final ResourceService resourceService = new ResourceService();
    private final AppointmentService appointmentService = new AppointmentService();

    /**
     * Returns the total number of users.
     *
     * @return the total user count
     */
    public int getTotalUsers() {
        return adminUserService.getTotalUsers();
    }

    /**
     * Returns the number of active users.
     *
     * @return the active user count
     */
    public int getActiveUsers() {
        return adminUserService.getActiveUsersCount();
    }

    /**
     * Returns the number of pending users.
     *
     * @return the pending user count
     */
    public int getPendingUsers() {
        return adminUserService.getPendingUsersCount();
    }

    /**
     * Returns the total number of mood entries.
     *
     * @return the total mood entry count
     */
    public int getTotalMoodEntries() {
        return moodEntryService.getTotalMoodEntriesCount();
    }

    /**
     * Returns the average mood score across all users.
     *
     * @return the average mood score
     */
    public double getAverageMoodScore() {
        return moodEntryService.getAverageMoodScore();
    }

    /**
     * Returns the mood distribution map.
     *
     * @return the mood score distribution
     */
    public Map<Integer, Integer> getMoodDistribution() {
        return moodEntryService.getMoodDistribution();
    }

    /**
     * Retrieves the top counselors by usage.
     *
     * @param limit the maximum number of counselors to return
     * @return the list of counselor summary rows
     */
    public List<Object[]> getTopCounselors(int limit) {
        return counselorService.getTopCounselors(limit);
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
     * Returns the number of published resources.
     *
     * @return the published resource count
     */
    public int getPublishedResources() {
        return resourceService.getPublishedResourcesCount();
    }

    /**
     * Returns the total number of appointments.
     *
     * @return the total appointment count
     */
    public int getTotalAppointments() {
        return appointmentService.getTotalAppointmentsCount();
    }
}