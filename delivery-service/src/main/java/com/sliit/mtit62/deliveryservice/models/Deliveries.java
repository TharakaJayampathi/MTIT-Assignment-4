package com.sliit.mtit62.deliveryservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Deliveries {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private Integer UserId;
    private Integer OrderId;
    private String DeliveryStatus;

    public Deliveries(int id, int orderId, int userId, String deliveryStatus) {
        Id = id;
        UserId = userId;
        OrderId = orderId;
        DeliveryStatus = deliveryStatus;
    }

    public Deliveries() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getOrderId() {
        return OrderId;
    }

    public void setOrderId(Integer orderId) {
        OrderId = orderId;
    }

    public String getDeliveryStatus() {
        return DeliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        DeliveryStatus = deliveryStatus;
    }
}

