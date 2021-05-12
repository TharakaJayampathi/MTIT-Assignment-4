package com.sliit.mtit62.userservice.dto;

public class UserResponse {

    private String UserId;
    private String ProductName;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }
}
