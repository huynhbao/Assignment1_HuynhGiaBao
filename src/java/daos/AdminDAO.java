/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.ProductDTO;
import dtos.RecordDTO;
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
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import utils.DBUtils;
import utils.MyConstants;

/**
 *
 * @author HuynhBao
 */
public class AdminDAO {
    
    private static final Logger LOGGER = Logger.getLogger(AdminDAO.class);

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
                String sql = "SELECT productID, p.name, quantity, price, imgLink, description, p.categoryID, c.name, date, expiryDate, status, COUNT(*) OVER() FROM tblProducts p JOIN tblCategories c ON p.categoryID = c.categoryID ORDER BY date DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, start);
                stm.setInt(2, MyConstants.recordPerPage);
                rs = stm.executeQuery();
                int totalRows = 0;
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    totalRows = rs.getInt(12);
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
                    boolean status = rs.getBoolean("status");

                    if (imgLink == null || "".equals(imgLink)) {
                        imgLink = MyConstants.defaultImgLink;
                    }
                    ProductDTO product = new ProductDTO(productID, name, quantity, price, date, expiryDate, imgLink, description, categoryID, categoryName, status);
                    list.add(product);
                }

                result.put(totalRows, list);
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

    public void createProduct(ProductDTO product) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblProducts(productID, name, description, quantity, price, date, expiryDate, imgLink, categoryID, status) VALUES(?,?,?,?,?,?,?,?,?,'true')";
                stm = conn.prepareStatement(sql);
                stm.setString(1, product.getProductID());
                stm.setString(2, product.getName());
                stm.setString(3, product.getDescription());
                stm.setInt(4, product.getQuantity());
                stm.setFloat(5, product.getPrice());
                Date curDate = new Date();
                Timestamp date = new Timestamp(curDate.getTime());
                Timestamp expiryDate = new Timestamp(product.getExpiryDate().getTime());
                stm.setTimestamp(6, date);
                stm.setTimestamp(7, expiryDate);
                stm.setString(8, product.getImgLink());
                stm.setString(9, product.getCategoryID());
                stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    public void updateProduct(ProductDTO product) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblProducts SET name = ?, description = ?, quantity = ?, price = ?, categoryID = ?, expiryDate = ?";
                if (product.getImgLink() != null) {
                    sql += ", imgLink = ?";
                }
                sql += " WHERE productID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, product.getName());
                stm.setString(2, product.getDescription());
                stm.setInt(3, product.getQuantity());
                stm.setFloat(4, product.getPrice());
                stm.setString(5, product.getCategoryID());
                Timestamp expiryDate = new Timestamp(product.getExpiryDate().getTime());
                stm.setTimestamp(6, expiryDate);
                int count = 7;
                if (product.getImgLink() != null) {
                    stm.setString(count++, product.getImgLink());
                }
                stm.setString(count, product.getProductID());
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

    public void setStatusProduct(String productID, boolean status) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblProducts SET status = ? WHERE productID = ?";
                stm = conn.prepareStatement(sql);
                stm.setBoolean(1, status);
                stm.setString(2, productID);
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

    public void recordAction(RecordDTO record) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblHistory(date, userID, productID, action, content) VALUES(?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                Timestamp time = new Timestamp(record.getDate().getTime());
                stm.setTimestamp(1, time);
                stm.setString(2, record.getUserID());
                stm.setString(3, record.getProductID());
                stm.setString(4, record.getAction());
                stm.setString(5, record.getContent());
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
