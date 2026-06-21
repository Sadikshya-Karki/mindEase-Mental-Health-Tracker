package com.mindease.dao;

import com.mindease.model.Counselor;
import com.mindease.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access operations for counselor records.
 */
public class CounselorDAO {

    /**
     * Counts counselors with active status.
     *
     * @return active counselor count, or {@code 0} on failure
     */
    public int getActiveCounselorsCount() {
        String sql = "SELECT COUNT(*) FROM counselors WHERE status = 'active'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Retrieves all counselors.
     *
     * @return counselor list, or an empty list on failure
     */
    public List<Counselor> getAllCounselors() {
        List<Counselor> list = new ArrayList<>();
        String sql = "SELECT * FROM counselors ORDER BY counselor_id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapCounselor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieves only active counselors.
     *
     * @return active counselor list, or an empty list on failure
     */
    public List<Counselor> getAllActiveCounselors() {
        List<Counselor> list = new ArrayList<>();
        String sql = "SELECT * FROM counselors WHERE status = 'active' ORDER BY name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapCounselor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieves a counselor by id.
     *
     * @param id counselor id
     * @return matching counselor, or {@code null} if not found or on failure
     */
    public Counselor getCounselorById(int id) {
        String sql = "SELECT * FROM counselors WHERE counselor_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapCounselor(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a new counselor.
     *
     * @param c counselor payload
     * @return {@code true} if insert succeeded
     */
    public boolean createCounselor(Counselor c) {
        String sql = "INSERT INTO counselors (name, specialization, email, phone, available_days, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getSpecialization());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getPhone());
            ps.setString(5, c.getAvailableDays());
            ps.setString(6, c.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing counselor.
     *
     * @param c counselor payload
     * @return {@code true} if update succeeded
     */
    public boolean updateCounselor(Counselor c) {
        String sql = "UPDATE counselors SET name=?, specialization=?, email=?, phone=?, available_days=?, status=? WHERE counselor_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getSpecialization());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getPhone());
            ps.setString(5, c.getAvailableDays());
            ps.setString(6, c.getStatus());
            ps.setInt(7, c.getCounselorId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sets a counselor status to inactive.
     *
     * @param id counselor id
     * @return {@code true} if update succeeded
     */
    public boolean deactivateCounselor(int id) {
        return updateCounselorStatus(id, "inactive");
    }

    /**
     * Sets a counselor status to active.
     *
     * @param id counselor id
     * @return {@code true} if update succeeded
     */
    public boolean activateCounselor(int id) {
        return updateCounselorStatus(id, "active");
    }

    /**
     * Returns top counselors ordered by appointment volume.
     *
     * @param limit maximum number of rows
     * @return list of rows where index 0 is counselor name and index 1 is total count
     */
    public List<Object[]> getTopCounselors(int limit) {
        List<Object[]> rows = new ArrayList<>();
        String sql = "SELECT c.name, COUNT(a.appt_id) AS total " +
                "FROM appointments a JOIN counselors c ON a.counselor_id = c.counselor_id " +
                "GROUP BY c.counselor_id, c.name ORDER BY total DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rows.add(new Object[]{rs.getString("name"), rs.getInt("total")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    private boolean updateCounselorStatus(int id, String status) {
        String sql = "UPDATE counselors SET status = ? WHERE counselor_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Counselor mapCounselor(ResultSet rs) throws SQLException {
        Counselor c = new Counselor();
        c.setCounselorId(rs.getInt("counselor_id"));
        c.setName(rs.getString("name"));
        c.setSpecialization(rs.getString("specialization"));
        c.setEmail(rs.getString("email"));
        c.setPhone(rs.getString("phone"));
        c.setAvailableDays(rs.getString("available_days"));
        c.setStatus(rs.getString("status"));
        return c;
    }
}

