/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.Date;
import java.util.List;

/**
 *
 * @author HuynhBao
 */
public class OrderDTO {
    private int orderID;
    private String userID;
    private Date date;
    private float total;
    private List<OrderDetailDTO> orderDetail;
    
    public OrderDTO() {
    }

    public OrderDTO(int orderID, String userID, Date date, float total, List<OrderDetailDTO> orderDetail) {
        this.orderID = orderID;
        this.userID = userID;
        this.date = date;
        this.total = total;
        this.orderDetail = orderDetail;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }

    
}
