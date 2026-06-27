package com.mindease.service;

import com.mindease.dao.CounselorDAO;
import com.mindease.model.Counselor;

import java.util.List;

/**
 * Provides counselor management operations.
 */
public class CounselorService {
    private final CounselorDAO counselorDAO = new CounselorDAO();

    /**
     * Retrieves all counselors.
     *
     * @return the list of counselors
     */
    public List<Counselor> getAllCounselors() {
        return counselorDAO.getAllCounselors();
    }

    /**
     * Retrieves all active counselors.
     *
     * @return the list of active counselors
     */
    public List<Counselor> getAllActiveCounselors() {
        return counselorDAO.getAllActiveCounselors();
    }

    /**
     * Retrieves a counselor by id.
     *
     * @param id the counselor id
     * @return the counselor or null when not found
     */
    public Counselor getCounselorById(int id) {
        return counselorDAO.getCounselorById(id);
    }

    /**
     * Creates a counselor record.
     *
     * @param counselor the counselor to create
     * @return true when creation succeeds; false otherwise
     */
    public boolean createCounselor(Counselor counselor) {
        return counselorDAO.createCounselor(counselor);
    }

    /**
     * Updates a counselor record.
     *
     * @param counselor the counselor to update
     * @return true when update succeeds; false otherwise
     */
    public boolean updateCounselor(Counselor counselor) {
        return counselorDAO.updateCounselor(counselor);
    }

    /**
     * Deactivates a counselor.
     *
     * @param id the counselor id
     * @return true when deactivation succeeds; false otherwise
     */
    public boolean deactivateCounselor(int id) {
        return counselorDAO.deactivateCounselor(id);
    }

    /**
     * Activates a counselor.
     *
     * @param id the counselor id
     * @return true when activation succeeds; false otherwise
     */
    public boolean activateCounselor(int id) {
        return counselorDAO.activateCounselor(id);
    }

    /**
     * Returns the number of active counselors.
     *
     * @return the active counselor count
     */
    public int getActiveCounselorsCount() {
        return counselorDAO.getActiveCounselorsCount();
    }

    /**
     * Retrieves the top counselors by usage.
     *
     * @param limit the maximum number of counselors to return
     * @return the list of counselor summary rows
     */
    public List<Object[]> getTopCounselors(int limit) {
        return counselorDAO.getTopCounselors(limit);
    }
}
