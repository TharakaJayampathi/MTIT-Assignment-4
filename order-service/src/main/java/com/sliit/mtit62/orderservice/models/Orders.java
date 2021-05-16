package com.sliit.mtit62.orderservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Orders {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private Integer ProductId;
    private Integer UserId;
    private Integer Quantity;

    public Orders(){}

    public Orders(Integer id, Integer productId, Integer userId, Integer quantity) {
        Id = id;
        ProductId = productId;
        UserId = userId;
        Quantity = quantity;

    }

    public Orders(Integer productId) {
        ProductId = productId;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getProductId() {
        return ProductId;
    }

    public void setProductId(Integer productId) {
        ProductId = productId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    @Override
    public String toString() {
        return  "Order Id = " + this.UserId + ", " +
                "product Id = " + this.ProductId + ", " +
                "user Id = " + this.UserId + ", " +
                "Quantity = " + this.Quantity;
    }

}
