package com.sliit.mtit62.customerservice.dto;

public class CustomerResponse {

    private String CustomerId;
    private String ProductName;


    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }
}
