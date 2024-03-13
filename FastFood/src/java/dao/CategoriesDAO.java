package dao;

import java.util.*;
import model.*;

public class CategoriesDAO extends MyDAO {



    // lay ra tat ca category
    public Vector<Categories> getCategories() {
        Vector<Categories> categoriesList = new Vector<>();
        xSql = "SELECT * FROM Categories";
        try {
            String xName;
            int xId;
            Categories x;
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                xId = rs.getInt("ID");
                xName = rs.getString("Name");
                x = new Categories(xId, xName);
                categoriesList.add(x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoriesList;
    }

    // lay ra category cua 1 san pham
    public String getCategoryByProductId(int productId) {
        String categoryName = null;
        xSql = "SELECT c.Name FROM Categories c "
                + "INNER JOIN Products p ON c.ID = p.CategoryID "
                + "WHERE p.ID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                categoryName = rs.getString("Name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryName;
    }

    // thêm moi 1 category
    public void addCategory(String categoryName) {
        xSql = "INSERT INTO Categories (Name) VALUES (?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, categoryName);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // cap nhat category
    public void updateCategory(int cateID, String newName) {
        xSql = "UPDATE Categories SET Name = ? WHERE ID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, newName);
            ps.setInt(2, cateID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // xóa category
    public void deleteCategory(int cateID) {
        try {
            // Set foreign key references to null in other tables
            xSql = "UPDATE Products SET CategoryID = NULL WHERE CategoryID = ?";
            ps = con.prepareStatement(xSql);
            ps.setInt(1, cateID);
            ps.executeUpdate();

            // Delete the category from Categories table
            xSql = "DELETE FROM Categories WHERE ID = ?";
            ps = con.prepareStatement(xSql);
            ps.setInt(1, cateID);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
