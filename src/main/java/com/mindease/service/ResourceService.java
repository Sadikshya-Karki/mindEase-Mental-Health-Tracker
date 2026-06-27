package com.mindease.service;

import com.mindease.dao.BookmarkDAO;
import com.mindease.dao.CategoryDAO;
import com.mindease.dao.ResourceDAO;
import com.mindease.model.Category;
import com.mindease.model.Resource;

import java.util.List;

/**
 * Provides resource management operations.
 */
public class ResourceService {

    private final ResourceDAO resourceDAO = new ResourceDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final BookmarkDAO bookmarkDAO = new BookmarkDAO();

    /**
     * Retrieves all resources.
     *
     * @return the list of resources
     */
    public List<Resource> getAllResources() {
        return resourceDAO.getAllResources();
    }

    /**
     * Retrieves resources filtered by search, category, or status.
     *
     * @param search the search term
     * @param categoryId the category id
     * @param status the status filter
     * @return the list of matching resources
     */
    public List<Resource> getFilteredResources(String search, Integer categoryId, String status) {
        return resourceDAO.getFilteredResources(search, categoryId, status);
    }

    /**
     * Retrieves published resources for browsing.
     *
     * @param searchQuery the search query
     * @param categoryId the category id (string form)
     * @param page the page number
     * @param limit the page size
     * @return the list of published resources
     */
    public List<Resource> getPublishedResources(String searchQuery, String categoryId, String page, int limit) {
        return resourceDAO.getPublishedResources(searchQuery, categoryId, page, limit);
    }

    /**
     * Retrieves a resource by id.
     *
     * @param id the resource id
     * @return the resource or null when not found
     */
    public Resource getResourceById(int id) {
        return resourceDAO.getResourceById(id);
    }

    /**
     * Creates a resource.
     *
     * @param resource the resource to create
     * @return the new resource id
     */
    public int createResource(Resource resource) {
        return resourceDAO.createResource(resource);
    }

    /**
     * Updates a resource.
     *
     * @param resource the resource to update
     * @return true when update succeeds; false otherwise
     */
    public boolean updateResource(Resource resource) {
        return resourceDAO.updateResource(resource);
    }

    /**
     * Deletes a resource by id.
     *
     * @param id the resource id
     * @return true when deletion succeeds; false otherwise
     */
    public boolean deleteResource(int id) {
        return resourceDAO.deleteResource(id);
    }

    /**
     * Updates the status of a resource.
     *
     * @param id the resource id
     * @param status the new status
     * @return true when update succeeds; false otherwise
     */
    public boolean updateStatus(int id, String status) {
        return resourceDAO.updateStatus(id, status);
    }

    /**
     * Returns the number of published resources for a search query and category.
     *
     * @param searchQuery the search query
     * @param categoryId the category id (string form)
     * @return the matching published resource count
     */
    public int getPublishedResourcesCount(String searchQuery, String categoryId) {
        return resourceDAO.getPublishedResourcesCount(searchQuery, categoryId);
    }

    /**
     * Returns the total number of resources.
     *
     * @return the total resource count
     */
    public int getTotalResourcesCount() {
        return resourceDAO.getTotalResourcesCount();
    }

    /**
     * Returns the total number of published resources.
     *
     * @return the published resource count
     */
    public int getPublishedResourcesCount() {
        return resourceDAO.getPublishedResourcesCount();
    }

    /**
     * Retrieves all categories.
     *
     * @return the list of categories
     */
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    /**
     * Retrieves bookmarked resource ids for a user.
     *
     * @param userId the user id
     * @return the list of bookmarked resource ids
     */
    public List<Integer> getBookmarkedResourceIdsByUserId(int userId) {
        return bookmarkDAO.getBookmarkedResourceIdsByUserId(userId);
    }

    /**
     * Validates a resource and returns an error message when invalid.
     *
     * @param resource the resource to validate
     * @return an error message or null when valid
     */
    public String validateResource(Resource resource) {
        if (resource == null) {
            return "Invalid resource data.";
        }
        if (resource.getTitle() == null || resource.getTitle().trim().isEmpty()) {
            return "Title is required.";
        }
        if (resource.getUrl() == null || resource.getUrl().trim().isEmpty()) {
            return "Resource URL is required.";
        }

        String url = resource.getUrl().trim().toLowerCase();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "URL must start with http:// or https://";
        }

        if (resource.getImageUrl() != null && !resource.getImageUrl().trim().isEmpty()) {
            String imageUrl = resource.getImageUrl().trim().toLowerCase();
            if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
                return "Image URL must start with http:// or https://";
            }
        }

        return null;
    }

    /**
     * Normalizes resource fields in-place before persistence.
     *
     * @param resource the resource to normalize
     */
    public void normalizeResource(Resource resource) {
        if (resource == null) {
            return;
        }

        resource.setTitle(trimToNull(resource.getTitle()));
        resource.setDescription(trimToNull(resource.getDescription()));
        resource.setUrl(trimToNull(resource.getUrl()));
        resource.setImageUrl(trimToNull(resource.getImageUrl()));
        resource.setReadTime(trimToNull(resource.getReadTime()));
        resource.setTags(normalizeTags(resource.getTags()));

        String status = trimToNull(resource.getStatus());
        if (status == null || (!"published".equalsIgnoreCase(status) && !"draft".equalsIgnoreCase(status))) {
            resource.setStatus("draft");
        } else {
            resource.setStatus(status.toLowerCase());
        }

        if (resource.getReadTime() == null) {
            resource.setReadTime("5 min read");
        }
    }

    /**
     * Normalizes a comma-separated tag list.
     *
     * @param tags the raw tag string
     * @return the normalized tag string
     */
    private String normalizeTags(String tags) {
        String cleaned = trimToNull(tags);
        if (cleaned == null) {
            return null;
        }

        String[] parts = cleaned.split(",");
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            String item = trimToNull(part);
            if (item == null) {
                continue;
            }
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(item);
        }
        return builder.length() == 0 ? null : builder.toString();
    }

    /**
     * Trims a string or returns null when empty.
     *
     * @param value the raw string value
     * @return the trimmed string or null when empty
     */
    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}

