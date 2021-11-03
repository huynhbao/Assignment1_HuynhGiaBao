/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.Date;

/**
 *
 * @author HuynhBao
 */
public class RecordDTO {
    private int historyID;
    private Date date;
    private String userID;
    private String productID;
    private String action;
    private String content;

    public RecordDTO() {
    }

    public RecordDTO(int historyID, Date date, String userID, String productID, String action, String content) {
        this.historyID = historyID;
        this.date = date;
        this.userID = userID;
        this.productID = productID;
        this.action = action;
        this.content = content;
    }

    public int getHistoryID() {
        return historyID;
    }

    public void setHistoryID(int historyID) {
        this.historyID = historyID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}
