package com.sliit.mtit62.customerservice.service;
import com.sliit.mtit62.customerservice.dto.CustomerRequest;
import com.sliit.mtit62.customerservice.dto.CustomerResponse;
import com.sliit.mtit62.customerservice.dto.ProductCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomerServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    public CustomerResponse createCustomer(CustomerRequest customerRequest){

        var productCreationRequest = new ProductCreationRequest();
        productCreationRequest.setName(customerRequest.getName());
        productCreationRequest.setPrice(customerRequest.getPrice());
        productCreationRequest.setDescription(customerRequest.getDescription());
        ResponseEntity<String> productCreationResponse = restTemplate.postForEntity("http://localhost:8081/api/products/",productCreationRequest, String.class);

        var customerResponse = new CustomerResponse();
        customerResponse.setCustomerId(UUID.randomUUID().toString());
        customerResponse.setProductName(customerRequest.getName());


        return customerResponse;
    }

    public HttpEntity<String> getAllProducts(){

        ResponseEntity<String> productCreationResponse = restTemplate.getForEntity("http://localhost:8081/api/products/", String.class);

        return productCreationResponse;
    }

    public String deleteProduct(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        restTemplate.delete("http://localhost:8081/api/products/{id}", params);
        return "Product Deleted" + params;
    }


}
