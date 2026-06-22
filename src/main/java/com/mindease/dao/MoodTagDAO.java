package com.mindease.dao;

import com.mindease.model.MoodTag;
import com.mindease.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access operations for mood tags and mood entry tag links.
 */
public class MoodTagDAO {

    /**
     * Retrieves all available mood tags.
     *
     * @return mood tags, or an empty list on failure
     */
    public List<MoodTag> getAllTags() {
        List<MoodTag> tags = new ArrayList<>();
        String sql = "SELECT tag_id, tag_name FROM mood_tags ORDER BY tag_name ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MoodTag tag = new MoodTag();
                tag.setTagId(rs.getInt("tag_id"));
                tag.setTagName(rs.getString("tag_name"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }

    /**
     * Saves multiple tag links for a mood entry.
     *
     * @param entryId mood entry id
     * @param tagIds tag ids to associate
     */
    public void saveEntryTags(int entryId, List<Integer> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO mood_entry_tags (entry_id, tag_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Integer tagId : tagIds) {
                if (tagId == null) {
                    continue;
                }
                ps.setInt(1, entryId);
                ps.setInt(2, tagId);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes all tag links for a mood entry.
     *
     * @param entryId mood entry id
     */
    public void deleteEntryTags(int entryId) {
        String sql = "DELETE FROM mood_entry_tags WHERE entry_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, entryId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
