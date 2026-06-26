package com.mindease.service;

import com.mindease.dao.BookmarkDAO;
import com.mindease.model.Resource;

import java.util.List;

/**
 * Provides bookmark operations for saved resources.
 */
public class BookmarkService {
    private final BookmarkDAO bookmarkDAO = new BookmarkDAO();

    /**
     * Retrieves bookmarked resource IDs for a user.
     *
     * @param userId the user id
     * @return the list of bookmarked resource ids
     */
    public List<Integer> getBookmarkedResourceIdsByUserId(int userId) {
        return bookmarkDAO.getBookmarkedResourceIdsByUserId(userId);
    }

    /**
     * Retrieves bookmarked resources for a user.
     *
     * @param userId the user id
     * @return the list of bookmarked resources
     */
    public List<Resource> getBookmarkedResourcesByUserId(int userId) {
        return bookmarkDAO.getBookmarkedResourcesByUserId(userId);
    }

    /**
     * Adds a bookmark for the given user and resource.
     *
     * @param userId the user id
     * @param resourceId the resource id
     * @return true when the bookmark is added; false otherwise
     */
    public boolean addBookmark(int userId, int resourceId) {
        return bookmarkDAO.addBookmark(userId, resourceId);
    }

    /**
     * Removes a bookmark for the given user and resource.
     *
     * @param userId the user id
     * @param resourceId the resource id
     * @return true when the bookmark is removed; false otherwise
     */
    public boolean removeBookmark(int userId, int resourceId) {
        return bookmarkDAO.removeBookmark(userId, resourceId);
    }

    /**
     * Returns the number of saved resources for a user.
     *
     * @param userId the user id
     * @return the saved resource count
     */
    public int getSavedResourcesCount(int userId) {
        return bookmarkDAO.getSavedResourcesCount(userId);
    }

    /**
     * Retrieves a limited number of saved resources for a user.
     *
     * @param userId the user id
     * @param limit the maximum number of resources to return
     * @return the list of saved resources
     */
    public List<Resource> getSavedResources(int userId, int limit) {
        return bookmarkDAO.getSavedResources(userId, limit);
    }
}
