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
public class ProductDTO {

    private String productID;
    private String name;
    private int quantity;
    private float price;
    private Date date;
    private Date expiryDate;
    private String imgLink;
    private String description;
    private String categoryID;
    private String categoryName;
    private Boolean status;

    public ProductDTO() {
    }

    public ProductDTO(String productID, String name, int quantity, float price, Date date, Date expiryDate, String imgLink, String description, String categoryID, String categoryName, Boolean status) {
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.expiryDate = expiryDate;
        this.imgLink = imgLink;
        this.description = description;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.status = status;
    }

    public ProductDTO(ProductDTO newProduct) {
        this.productID = newProduct.productID;
        this.name = newProduct.name;
        this.quantity = newProduct.quantity;
        this.price = newProduct.price;
        this.date = newProduct.date;
        this.expiryDate = newProduct.expiryDate;
        this.imgLink = newProduct.imgLink;
        this.description = newProduct.description;
        this.categoryID = newProduct.categoryID;
        this.categoryName = newProduct.categoryName;
        this.status = newProduct.status;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
