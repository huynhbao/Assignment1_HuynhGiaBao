/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author HuynhBao
 */
public class CartDTO {
    private String userID;
    private Map<String, ProductDTO> cart;

    public CartDTO() {
    }

    public CartDTO(String userID, Map<String, ProductDTO> cart) {
        this.userID = userID;
        this.cart = cart;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Map<String, ProductDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, ProductDTO> cart) {
        this.cart = cart;
    }
    
    public void add(ProductDTO product) {
        if (this.cart == null) {
            this.cart = new HashMap<>();
        }
        if (this.cart.containsKey(product.getProductID())) {
            int quantity = this.cart.get(product.getProductID()).getQuantity();
            product.setQuantity(quantity + product.getQuantity());
        }
        this.cart.put(product.getProductID(), product);
    }
    
    public void delete(String id) {
        if (this.cart == null) {
            return;
        }
        if (this.cart.containsKey(id)) {
            this.cart.remove(id);
        }
    }
    
    public void update(ProductDTO product) {
        if (this.cart != null) {
            if (this.cart.containsKey(product.getProductID())) {
                this.cart.replace(product.getProductID(), product);
            }
        }
    }
    
}
