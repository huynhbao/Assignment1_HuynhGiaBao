/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author HuynhBao
 */
public class OrderDetailDTO {
    private int orderDetailID;
    private ProductDTO product;

    public OrderDetailDTO(int orderDetailID, ProductDTO product) {
        this.orderDetailID = orderDetailID;
        this.product = product;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
    
    
}
