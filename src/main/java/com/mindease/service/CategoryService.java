package com.mindease.service;

import com.mindease.dao.CategoryDAO;
import com.mindease.model.Category;

/**
 * Provides category management operations.
 */
public class CategoryService {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    /**
     * Creates a category after validating required fields.
     *
     * @param name the category name
     * @param description the category description
     * @return a status message or "success" when creation completes
     */
    public String addCategory(String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            return "Category name is required.";
        }
        Category c = new Category();
        c.setName(name.trim());
        c.setDescription(description == null ? "" : description.trim());
        return categoryDAO.createCategory(c) ? "success" : "Failed to create category.";
    }

    /**
     * Updates a category after validating required fields.
     *
     * @param categoryId the category id
     * @param name the category name
     * @param description the category description
     * @return a status message or "success" when update completes
     */
    public String updateCategory(int categoryId, String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            return "Category name is required.";
        }
        Category c = new Category();
        c.setCategoryId(categoryId);
        c.setName(name.trim());
        c.setDescription(description == null ? "" : description.trim());
        return categoryDAO.updateCategory(c) ? "success" : "Failed to update category.";
    }

    /**
     * Deletes a category by id.
     *
     * @param id the category id
     * @return true when deletion succeeds; false otherwise
     */
    public boolean deleteCategory(int id) {
        return categoryDAO.deleteCategory(id);
    }
}
