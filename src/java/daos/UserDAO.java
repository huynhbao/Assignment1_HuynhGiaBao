/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.CartDTO;
import dtos.OrderDTO;
import dtos.OrderDetailDTO;
import dtos.ProductDTO;
import dtos.SearchDTO;
import dtos.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import utils.DBUtils;
import utils.MyConstants;

/**
 *
 * @author admin
 */
public class UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class);
    
    public UserDTO checkLogin(String userID, String password) throws SQLException {
        UserDTO result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT name, email, phone, address, status, roleID FROM tblUsers WHERE userID = ? AND password = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, password);
                rs = stm.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    Boolean status = rs.getBoolean("status");
                    String roleID = rs.getString("roleID");
                    result = new UserDTO(userID, name, "", email, phone, address, status, roleID);
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

    public UserDTO loginGmail(UserDTO user) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sqlCheckAccount = "SELECT name, email, phone, address, status, roleID FROM tblUsers WHERE userID=?";
                stm = conn.prepareStatement(sqlCheckAccount);
                stm.setString(1, user.getUserID());
                rs = stm.executeQuery();

                if (!rs.next()) {
                    String sql = "INSERT INTO tblUsers(userID, name, password, email, phone, address, status, roleID) VALUES(?,?,?,?,?,?, ?, ?)";
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, user.getUserID());
                    stm.setString(2, user.getName());
                    stm.setString(3, user.getPassword());
                    stm.setString(4, user.getEmail());
                    stm.setString(5, user.getPhone());
                    stm.setString(6, user.getAddress());
                    stm.setBoolean(7, user.getStatus());
                    stm.setString(8, user.getRoleID());
                    stm.executeUpdate();
                } else {
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    user.setStatus(rs.getBoolean("status"));
                    user.setRoleID(rs.getString("roleID"));
                }

                user.setPassword("");

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
        return user;
    }

    public int checkout(CartDTO cart) throws SQLException {
        int result = -1;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String orderIdArr[] = {"orderID"};
                String sql = "INSERT INTO tblOrders(userID, date, total) VALUES(?,?,?)";
                Date date = new Date();
                float total = 0;
                for (ProductDTO dto : cart.getCart().values()) {
                    total += dto.getQuantity() * dto.getPrice();
                }

                stm = conn.prepareStatement(sql, orderIdArr);
                stm.setString(1, cart.getUserID());
                Timestamp time = new Timestamp(date.getTime());
                stm.setTimestamp(2, time);
                stm.setFloat(3, total);
                stm.executeUpdate();
                rs = stm.getGeneratedKeys();
                ProductDAO dao = new ProductDAO();
                if (rs.next()) {
                    String sqlOrderDetail = "INSERT INTO tblOrderDetails(productID, quantity, price, orderID) VALUES(?,?,?,?)";
                    int orderID = rs.getInt(1);
                    for (ProductDTO dto : cart.getCart().values()) {
                        stm = conn.prepareStatement(sqlOrderDetail, orderID);
                        stm.setString(1, dto.getProductID());
                        stm.setInt(2, dto.getQuantity());
                        stm.setFloat(3, dto.getPrice());
                        stm.setInt(4, rs.getInt(1));
                        stm.executeUpdate();
                        dao.setProductQuantity(dto);
                    }
                    result = orderID;
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

    public OrderDTO getOrderDTO(int orderID) throws SQLException {
        OrderDTO order = null;
        List<OrderDetailDTO> list = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT total, date, productID, quantity, price, userID, orderDetailID FROM tblOrders o JOIN tblOrderDetails od ON o.orderID = od.orderID WHERE o.orderID = ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, orderID);
                rs = stm.executeQuery();

                ProductDAO dao = new ProductDAO();
                while (rs.next()) {
                    if (order == null) {
                        order = new OrderDTO();
                    }
                    if (list == null) {
                        list = new ArrayList<>();
                    }

                    int orderDetailID = rs.getInt("orderDetailID");
                    float total = rs.getFloat("total");
                    Timestamp timestamp = rs.getTimestamp("date");
                    Date date = new Date(timestamp.getTime());
                    String productID = rs.getString("productID");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    String userID = rs.getString("userID");

                    order.setOrderID(orderID);
                    order.setUserID(userID);
                    order.setDate(date);
                    order.setTotal(total);

                    if (orderID == order.getOrderID()) {
                        ProductDTO product = dao.getProductDTO(productID);
                        product.setQuantity(quantity);
                        product.setPrice(price);
                        OrderDetailDTO orderDetail = new OrderDetailDTO(orderDetailID, product);
                        list.add(orderDetail);

                        order.setOrderDetail(list);
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
        return order;
    }

    public List<OrderDetailDTO> getListOrderDetail(int orderID) throws SQLException {
        List<OrderDetailDTO> list = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT orderDetailID, productID, quantity, price FROM tblOrderDetails WHERE orderID = ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, orderID);
                rs = stm.executeQuery();

                ProductDAO dao = new ProductDAO();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }

                    int orderDetailID = rs.getInt("orderDetailID");
                    String productID = rs.getString("productID");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");

                    ProductDTO product = dao.getProductDTO(productID);
                    product.setQuantity(quantity);
                    product.setPrice(price);
                    OrderDetailDTO orderDetail = new OrderDetailDTO(orderDetailID, product);

                    list.add(orderDetail);
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
        return list;
    }

    public Map<Integer, List<OrderDTO>> getUserOrderHistory(String userID, int currentPage) throws SQLException {
        Map<Integer, List<OrderDTO>> result = new HashMap<>();
        List<OrderDTO> listOrder = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                int start = currentPage * MyConstants.recordPerPage - MyConstants.recordPerPage;
                String sql = "SELECT COUNT(*) OVER(), orderID, total, date FROM tblOrders WHERE userID = ? ORDER BY date DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setInt(2, start);
                stm.setInt(3, MyConstants.recordPerPage);
                rs = stm.executeQuery();

                int totalRow = 0;
                while (rs.next()) {
                    if (listOrder == null) {
                        listOrder = new ArrayList<>();
                    }
                    if (totalRow == 0) {
                        totalRow = rs.getInt(1);
                    }

                    int orderID = rs.getInt(2);
                    float total = rs.getFloat("total");
                    Timestamp timestamp = rs.getTimestamp("date");
                    Date date = new Date(timestamp.getTime());

                    OrderDTO order = new OrderDTO(orderID, userID, date, total, null);
                    order.setOrderDetail(getListOrderDetail(orderID));
                    listOrder.add(order);

                }

                result.put(totalRow, listOrder);
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

    public List<OrderDTO> getUserOrderByID(String userID, Set<Integer> setOrderID) throws SQLException {
        List<OrderDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT total, date FROM tblOrders WHERE userID = ? AND orderID = ?";
                stm = conn.prepareStatement(sql);
                for (Integer orderID : setOrderID) {
                    stm.setString(1, userID);
                    stm.setInt(2, orderID);
                    rs = stm.executeQuery();

                    if (rs.next()) {
                        if (result == null) {
                            result = new ArrayList<>();
                        }

                        float total = rs.getFloat("total");
                        Timestamp timestamp = rs.getTimestamp("date");
                        Date date = new Date(timestamp.getTime());

                        OrderDTO order = new OrderDTO(orderID, userID, date, total, null);
                        order.setOrderDetail(getListOrderDetail(orderID));
                        result.add(order);

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

    public Map<Integer, List<OrderDTO>> searchOrderHistory(int currentPage, String userID, String searchName, String dateFrom, String dateTo) throws SQLException {
        Map<Integer, List<OrderDTO>> result = new HashMap<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                int start = currentPage * MyConstants.recordPerPage - MyConstants.recordPerPage;
                List<SearchDTO> list = new ArrayList<>();

                int count = 3;

                String sql = "SELECT count(*) OVER(), orderID FROM "
                        + "(SELECT DISTINCT o.orderID, o.date, userID FROM tblOrders o left join tblOrderDetails od on od.orderID = o.orderID JOIN tblProducts p on p.name LIKE ? AND p.productID = od.productID) AS tbl "
                        + "WHERE userID = ?";

                if (!"".equals(dateFrom)) {
                    SearchDTO search = new SearchDTO(count++, "CAST(date AS Date) >= ?", dateFrom);
                    list.add(search);
                }
                if (!"".equals(dateTo)) {
                    SearchDTO search = new SearchDTO(count++, "CAST(date AS Date) <= ?", dateTo);
                    list.add(search);
                }

                for (SearchDTO searchDTO : list) {
                    sql = sql.concat(" AND " + searchDTO.getExpression());
                }

                sql = sql.concat(" ORDER BY date DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

                stm = conn.prepareStatement(sql);
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                if (searchName == null) {
                    searchName = "";
                }
                stm.setString(1, "%" + searchName + "%");

                for (SearchDTO searchDTO : list) {
                    if (searchDTO.getExpression().contains("date")) {
                        Date date = df.parse(searchDTO.getValue());
                        java.sql.Date sDate = new java.sql.Date(date.getTime());
                        stm.setDate(searchDTO.getIndex(), sDate);
                    } else {
                        stm.setString(searchDTO.getIndex(), searchDTO.getValue());
                    }
                }

                stm.setString(2, userID);
                stm.setInt(count++, start);
                stm.setInt(count++, MyConstants.recordPerPage);
                rs = stm.executeQuery();

                Set<Integer> setOrderID = new HashSet<>();
                int totalRow = 0;
                while (rs.next()) {
                    if (totalRow == 0) {
                        totalRow = rs.getInt(1);
                    }
                    int orderID = rs.getInt(2);
                    setOrderID.add(orderID);
                }

                List<OrderDTO> listOrder = getUserOrderByID(userID, setOrderID);

                result.put(totalRow, listOrder);

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

    public Map<Integer, List<OrderDTO>> searchOrderHistory(int currentPage, String userID, Date dateFrom, Date dateTo) throws SQLException {
        Map<Integer, List<OrderDTO>> result = new HashMap<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                int start = currentPage * MyConstants.recordPerPage - MyConstants.recordPerPage;

                String sql = "SELECT COUNT(*) OVER(), orderID, date, userID FROM tblOrders WHERE userID = ? AND CAST(date AS Date) >= ? AND CAST(date AS Date) <= ? ORDER BY date DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                java.sql.Date date1 = new java.sql.Date(dateFrom.getTime());
                stm.setDate(2, date1);
                java.sql.Date date2 = new java.sql.Date(dateTo.getTime());
                stm.setDate(3, date2);
                stm.setInt(4, start);
                stm.setInt(5, MyConstants.recordPerPage);
                rs = stm.executeQuery();

                Set<Integer> setOrderID = new HashSet<>();
                int totalRow = 0;
                while (rs.next()) {
                    if (totalRow == 0) {
                        totalRow = rs.getInt(1);
                    }
                    int orderID = rs.getInt(2);
                    setOrderID.add(orderID);
                }

                List<OrderDTO> listOrder = getUserOrderByID(userID, setOrderID);

                result.put(totalRow, listOrder);

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
}
