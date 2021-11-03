/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.CartDTO;
import dtos.CategoryDTO;
import dtos.ProductDTO;
import dtos.SearchDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import utils.DBUtils;
import utils.MyConstants;

/**
 *
 * @author HuynhBao
 */
public class ProductDAO {

    private static final Logger LOGGER = Logger.getLogger(ProductDAO.class);

    public Map<Integer, List<ProductDTO>> searchProduct(int currentPage, String searchName, String priceStart, String priceEnd, String category) throws SQLException {
        Map<Integer, List<ProductDTO>> result = new HashMap<>();
        List<ProductDTO> productList = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                int start = currentPage * MyConstants.recordPerPage - MyConstants.recordPerPage;
                List<SearchDTO> list = new ArrayList<>();
                int count = 1;
                String sql = "SELECT productID, p.name, quantity, price, imgLink, description, p.categoryID, c.name, date, expiryDate, COUNT(*) OVER() FROM tblProducts p JOIN tblCategories c ON p.categoryID = c.categoryID WHERE status = 1 and quantity > 0";
                if (!"".equals(searchName)) {
                    SearchDTO search = new SearchDTO(count++, "p.name LIKE ?", "%" + searchName + "%");
                    list.add(search);
                }
                if (!"".equals(priceStart)) {
                    SearchDTO search = new SearchDTO(count++, "price >= ?", priceStart);
                    list.add(search);
                }
                if (!"".equals(priceEnd)) {
                    SearchDTO search = new SearchDTO(count++, "price <= ?", priceEnd);
                    list.add(search);
                }
                if (!"".equals(category)) {
                    SearchDTO search = new SearchDTO(count++, "c.name = ?", category);
                    list.add(search);
                }

                for (SearchDTO searchDTO : list) {
                    sql = sql.concat(" AND " + searchDTO.getExpression());
                }

                sql = sql.concat(" ORDER BY date DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

                stm = conn.prepareStatement(sql);
                for (SearchDTO searchDTO : list) {
                    if (searchDTO.getExpression().contains("price")) {
                        Float price = Float.parseFloat(searchDTO.getValue());
                        stm.setFloat(searchDTO.getIndex(), price);
                    } else {
                        stm.setString(searchDTO.getIndex(), searchDTO.getValue());
                    }
                }
                stm.setInt(count++, start);
                stm.setInt(count++, MyConstants.recordPerPage);
                rs = stm.executeQuery();

                int totalProduct = 0;
                while (rs.next()) {
                    if (productList == null) {
                        productList = new ArrayList<>();
                    }
                    if (totalProduct == 0) {
                        totalProduct = rs.getInt(11);
                    }
                    String productID = rs.getString("productID");
                    String name = rs.getString(2);
                    int quantity = rs.getInt("quantity");
                    Float price = rs.getFloat("price");
                    String imgLink = rs.getString("imgLink");
                    String description = rs.getString("description");
                    String categoryID = rs.getString(7);
                    String categoryName = rs.getString(8);
                    
                    Timestamp dateTimestamp = rs.getTimestamp("date");
                    Timestamp expiryDateTimestamp = rs.getTimestamp("expiryDate");
                    Date date = new Date(dateTimestamp.getTime());
                    Date expiryDate = new Date(expiryDateTimestamp.getTime());

                    ProductDTO product = new ProductDTO(productID, name, quantity, price, date, expiryDate, imgLink, description, categoryID, categoryName, true);
                    productList.add(product);
                }

                result.put(totalProduct, productList);

            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public Map<Integer, List<ProductDTO>> getProducts(int currentPage) throws SQLException {
        Map<Integer, List<ProductDTO>> result = new HashMap<>();
        List<ProductDTO> list = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                int start = currentPage * MyConstants.recordPerPage - MyConstants.recordPerPage;
                String sql = "SELECT productID, p.name, quantity, price, imgLink, description, p.categoryID, c.name, date, expiryDate, COUNT(*) OVER() FROM tblProducts p JOIN tblCategories c ON p.categoryID = c.categoryID WHERE status = 1 and quantity > 0 ORDER BY date DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, start);
                stm.setInt(2, MyConstants.recordPerPage);
                rs = stm.executeQuery();
                int totalProduct = 0;
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    if (totalProduct == 0) {
                        totalProduct = rs.getInt(11);
                    }
                    String productID = rs.getString("productID");
                    String name = rs.getString(2);
                    int quantity = rs.getInt("quantity");
                    Float price = rs.getFloat("price");
                    String imgLink = rs.getString("imgLink");
                    String description = rs.getString("description");
                    String categoryID = rs.getString(7);
                    String categoryName = rs.getString(8);
                    Timestamp dateTimestamp = rs.getTimestamp("date");
                    Timestamp expiryDateTimestamp = rs.getTimestamp("expiryDate");
                    Date date = new Date(dateTimestamp.getTime());
                    Date expiryDate = new Date(expiryDateTimestamp.getTime());

                    ProductDTO product = new ProductDTO(productID, name, quantity, price, date, expiryDate, imgLink, description, categoryID, categoryName, true);
                    list.add(product);
                }
                
                result.put(totalProduct, list);
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }
    
    public Map<Integer, List<ProductDTO>> getProductsByCategory(int currentPage, String categoryID) throws SQLException {
        Map<Integer, List<ProductDTO>> result = new HashMap<>();
        List<ProductDTO> list = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                int start = currentPage * MyConstants.recordPerPage - MyConstants.recordPerPage;
                String sql = "SELECT productID, p.name, quantity, price, imgLink, description, c.name, date, expiryDate, COUNT(*) OVER() FROM tblProducts p JOIN tblCategories c ON p.categoryID = c.categoryID WHERE status = 1 and quantity > 0 AND p.categoryID = ? ORDER BY date DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setString(1, categoryID);
                stm.setInt(2, start);
                stm.setInt(3, MyConstants.recordPerPage);
                rs = stm.executeQuery();
                int totalProduct = 0;
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    if (totalProduct == 0) {
                        totalProduct = rs.getInt(10);
                    }
                    String productID = rs.getString("productID");
                    String name = rs.getString(2);
                    int quantity = rs.getInt("quantity");
                    Float price = rs.getFloat("price");
                    String imgLink = rs.getString("imgLink");
                    String description = rs.getString("description");
                    String categoryName = rs.getString(7);
                    
                    Timestamp dateTimestamp = rs.getTimestamp("date");
                    Timestamp expiryDateTimestamp = rs.getTimestamp("expiryDate");
                    Date date = new Date(dateTimestamp.getTime());
                    Date expiryDate = new Date(expiryDateTimestamp.getTime());

                    ProductDTO product = new ProductDTO(productID, name, quantity, price, date, expiryDate, imgLink, description, categoryID, categoryName, true);
                    list.add(product);
                }
                
                result.put(totalProduct, list);
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public ProductDTO getProductDTO(String productID) throws SQLException {
        ProductDTO result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT p.name, price, imgLink, description, p.categoryID, c.name, date, expiryDate FROM tblProducts p JOIN tblCategories c ON p.categoryID = c.categoryID WHERE productID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, productID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString(1);
                    Float price = rs.getFloat("price");
                    String imgLink = rs.getString("imgLink");
                    String description = rs.getString("description");
                    String categoryID = rs.getString(5);
                    String categoryName = rs.getString(6);
                    
                    Timestamp dateTimestamp = rs.getTimestamp("date");
                    Timestamp expiryDateTimestamp = rs.getTimestamp("expiryDate");
                    Date date = new Date(dateTimestamp.getTime());
                    Date expiryDate = new Date(expiryDateTimestamp.getTime());

                    result = new ProductDTO(productID, name, 1, price, date, expiryDate, imgLink, description, categoryID, categoryName, true);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public List<CategoryDTO> getCategories() throws SQLException {
        List<CategoryDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT categoryID, name FROM tblCategories";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    String categoryID = rs.getString("categoryID");
                    String name = rs.getString("name");

                    CategoryDTO category = new CategoryDTO(categoryID, name);
                    result.add(category);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

   
    public List<ProductDTO> getProductsOutOfStock(CartDTO cart) throws SQLException {
        List<ProductDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT productID, quantity FROM tblProducts WHERE productID = ?";
                stm = conn.prepareStatement(sql);
                for (ProductDTO product : cart.getCart().values()) {
                    stm.setString(1, product.getProductID());
                    rs = stm.executeQuery();
                    if (rs.next()) {
                        int quantity = rs.getInt("quantity");
                        if (quantity < product.getQuantity()) {
                            if (result == null) {
                                result = new ArrayList<>();
                            }
                            ProductDTO newProduct = new ProductDTO(product);
                            newProduct.setQuantity(quantity);
                            result.add(newProduct);
                        }

                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public void setProductQuantity(ProductDTO product) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblProducts SET quantity = quantity - ? WHERE productID = ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, product.getQuantity());
                stm.setString(2, product.getProductID());
                stm.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
