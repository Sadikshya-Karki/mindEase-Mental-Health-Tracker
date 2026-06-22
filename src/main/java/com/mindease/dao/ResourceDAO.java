package com.mindease.dao;

import com.mindease.model.Resource;
import com.mindease.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



    /**
 * Data access operations for content resources.
 */
public class ResourceDAO {

    private static final String BASE_JOIN_SELECT =
            "SELECT r.*, c.name AS categoryName, u.name AS authorName " +
            "FROM resources r " +
            "LEFT JOIN categories c ON r.category_id = c.category_id " +
            "LEFT JOIN users u ON r.added_by = u.user_id ";

    /**
     * Counts all resources.
     *
     * @return total resource count, or {@code 0} on failure
     */
    public int getTotalResourcesCount() {
        String sql = "SELECT COUNT(*) FROM resources";
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
     * Counts resources with published status.
     *
     * @return published resource count, or {@code 0} on failure
     */
    public int getPublishedResourcesCount() {
        String sql = "SELECT COUNT(*) FROM resources WHERE status = 'published'";
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
     * Retrieves all resources with category and author details.
     *
     * @return resources, or an empty list on failure
     */
    public List<Resource> getAllResources() {
        List<Resource> list = new ArrayList<>();
        String sql = BASE_JOIN_SELECT + "ORDER BY r.resource_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResource(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieves resources filtered by optional search, category, and status.
     *
     * @param search title or tag text filter
     * @param categoryId category id filter
     * @param status status filter
     * @return matching resources, or an empty list on failure
     */
    public List<Resource> getFilteredResources(String search, Integer categoryId, String status) {
        List<Resource> list = new ArrayList<>();

        String normalizedSearch = normalize(search);
        String normalizedStatus = normalize(status);
        StringBuilder sql = new StringBuilder(BASE_JOIN_SELECT + "WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (normalizedSearch != null) {
            sql.append("AND (r.title LIKE ? OR IFNULL(r.tags,'') LIKE ?) ");
            String like = "%" + normalizedSearch + "%";
            params.add(like);
            params.add(like);
        }

        if (categoryId != null) {
            sql.append("AND r.category_id = ? ");
            params.add(categoryId);
        }

        if (normalizedStatus != null) {
            sql.append("AND r.status = ? ");
            params.add(normalizedStatus);
        }

        sql.append("ORDER BY r.resource_id DESC");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            bindParams(ps, params);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResource(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Retrieves a resource by id.
     *
     * @param id resource id
     * @return matching resource, or {@code null} if not found or on failure
     */
    public Resource getResourceById(int id) {
        String sql = BASE_JOIN_SELECT + "WHERE r.resource_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapResource(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a resource and returns its generated id.
     *
     * @param r resource payload
     * @return generated id, or {@code 0} on failure
     */
    public int createResource(Resource r) {
        String sql = "INSERT INTO resources (title, description, category_id, url, added_by, status, read_time, image_url, tags) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, r.getTitle());
            ps.setString(2, r.getDescription());
            if (r.getCategoryId() > 0) {
                ps.setInt(3, r.getCategoryId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            ps.setString(4, r.getUrl());
            ps.setInt(5, r.getAddedBy());
            ps.setString(6, r.getStatus());
            ps.setString(7, r.getReadTime());
            ps.setString(8, r.getImageUrl());
            ps.setString(9, r.getTags());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                return 0;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Updates an existing resource.
     *
     * @param r resource payload
     * @return {@code true} if update succeeded
     */
    public boolean updateResource(Resource r) {
        String sql = "UPDATE resources SET title=?, description=?, category_id=?, url=?, status=?, read_time=?, image_url=?, tags=?, added_by=? " +
                "WHERE resource_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getTitle());
            ps.setString(2, r.getDescription());
            if (r.getCategoryId() > 0) {
                ps.setInt(3, r.getCategoryId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            ps.setString(4, r.getUrl());
            ps.setString(5, r.getStatus());
            ps.setString(6, r.getReadTime());
            ps.setString(7, r.getImageUrl());
            ps.setString(8, r.getTags());
            ps.setInt(9, r.getAddedBy());
            ps.setInt(10, r.getResourceId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a resource by id.
     *
     * @param id resource id
     * @return {@code true} if delete succeeded
     */
    public boolean deleteResource(int id) {
        String sql = "DELETE FROM resources WHERE resource_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates a resource status.
     *
     * @param id resource id
     * @param status status value
     * @return {@code true} if update succeeded
     */
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE resources SET status = ? WHERE resource_id = ?";
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



    /**
     * Retrieves paged published resources for the user portal.
     *
     * @param searchQuery optional search text
     * @param categoryId optional category id or {@code all}
     * @param page page number string
     * @param limit page size
     * @return published resources, or an empty list on failure
     */
    public List<Resource> getPublishedResources(String searchQuery, String categoryId, String page, int limit) {
        List<Resource> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT r.*, c.name AS categoryName, c.color_code AS colorCode, u.name AS authorName " +
                "FROM resources r " +
                "LEFT JOIN categories c ON r.category_id = c.category_id " +
                "LEFT JOIN users u ON r.added_by = u.user_id " +
                "WHERE r.status = 'published' ");

        boolean hasSearch = searchQuery != null && !searchQuery.trim().isEmpty();
        boolean hasCategory = categoryId != null && !categoryId.trim().isEmpty() && !"all".equalsIgnoreCase(categoryId.trim());

        if (hasSearch) {
            sql.append("AND (r.title LIKE ? OR r.description LIKE ? OR IFNULL(r.tags,'') LIKE ?) ");
        }

        if (hasCategory) {
            sql.append("AND r.category_id = ? ");
        }

        sql.append("ORDER BY r.resource_id DESC LIMIT ? OFFSET ?");

        int pageNo = 1;
        try {
            pageNo = Integer.parseInt(page == null ? "1" : page.trim());
            if (pageNo < 1) {
                pageNo = 1;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            pageNo = 1;
        }

        int offset = (pageNo - 1) * limit;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (hasSearch) {
                String like = "%" + searchQuery.trim() + "%";
                ps.setString(idx++, like);
                ps.setString(idx++, like);
                ps.setString(idx++, like);
            }

            if (hasCategory) {
                ps.setInt(idx++, Integer.parseInt(categoryId.trim()));
            }

            ps.setInt(idx++, limit);
            ps.setInt(idx, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResource(rs));
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Counts published resources for the same filters used in portal listing.
     *
     * @param searchQuery optional search text
     * @param categoryId optional category id or {@code all}
     * @return matching published count, or {@code 0} on failure
     */
    public int getPublishedResourcesCount(String searchQuery, String categoryId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM resources r WHERE r.status = 'published' ");

        boolean hasSearch = searchQuery != null && !searchQuery.trim().isEmpty();
        boolean hasCategory = categoryId != null && !categoryId.trim().isEmpty() && !"all".equalsIgnoreCase(categoryId.trim());

        if (hasSearch) {
            sql.append("AND (r.title LIKE ? OR r.description LIKE ? OR IFNULL(r.tags,'') LIKE ?) ");
        }

        if (hasCategory) {
            sql.append("AND r.category_id = ? ");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (hasSearch) {
                String like = "%" + searchQuery.trim() + "%";
                ps.setString(idx++, like);
                ps.setString(idx++, like);
                ps.setString(idx++, like);
            }
            if (hasCategory) {
                ps.setInt(idx, Integer.parseInt(categoryId.trim()));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Resource mapResource(ResultSet rs) throws SQLException {
        Resource r = new Resource();
        r.setResourceId(rs.getInt("resource_id"));
        r.setTitle(rs.getString("title"));
        r.setDescription(rs.getString("description"));
        r.setCategoryId(rs.getInt("category_id"));
        r.setUrl(rs.getString("url"));
        r.setAddedBy(rs.getInt("added_by"));
        r.setStatus(rs.getString("status"));
        r.setCategoryName(rs.getString("categoryName"));
        String authorName = getNullable(rs, "authorName");
        if (authorName == null || authorName.isBlank()) {
            authorName = getNullable(rs, "addedByName");
        }
        if (authorName == null || authorName.isBlank()) {
            authorName = "Unknown";
        }
        r.setAuthorName(authorName);
        String readTime = null;
        try {
            readTime = rs.getString("read_time");
        } catch (SQLException e) {
            e.printStackTrace();
            // Column may not exist in some local DB snapshots.
        }
        r.setReadTime(readTime == null || readTime.isBlank() ? "5 min read" : readTime);
        try {
            r.setImageUrl(rs.getString("image_url"));
        } catch (SQLException e) {
            e.printStackTrace();
            r.setImageUrl(null);
        }
        try {
            r.setTags(rs.getString("tags"));
        } catch (SQLException e) {
            e.printStackTrace();
            r.setTags(null);
        }
        String colorCode = null;
        try {
            colorCode = rs.getString("colorCode");
        } catch (SQLException e) {
            e.printStackTrace();
            // If category color is unavailable, use fallback.
        }
        r.setColorCode(colorCode == null || colorCode.isBlank() ? "#ede9fe" : colorCode);
        return r;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private void bindParams(PreparedStatement ps, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object value = params.get(i);
            if (value instanceof Integer) {
                ps.setInt(i + 1, (Integer) value);
            } else {
                ps.setString(i + 1, String.valueOf(value));
            }
        }
    }

    private String getNullable(ResultSet rs, String column) {
        try {
            return rs.getString(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

