package com.sliit.mtit62.userservice.services;
import com.sliit.mtit62.userservice.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    //Product Service
    public ResponseEntity<String> createProduct(UserRequest userRequest){

        var productCreationRequest = new ProductCreationRequest();
        productCreationRequest.setName(userRequest.getName());
        productCreationRequest.setPrice(userRequest.getPrice());
        productCreationRequest.setDescription(userRequest.getDescription());
        ResponseEntity<String> productCreationResponse = restTemplate.postForEntity("http://localhost:8081/api/products/",productCreationRequest, String.class);
        return productCreationResponse;
    }

    public ResponseEntity<String> updateProduct(UserRequest userRequest){

        var productCreationRequest = new ProductCreationRequest();
        productCreationRequest.setName(userRequest.getName());
        productCreationRequest.setPrice(userRequest.getPrice());
        productCreationRequest.setDescription(userRequest.getDescription());
        productCreationRequest.setId(userRequest.getId());
        ResponseEntity<String> productCreationResponse = restTemplate.postForEntity("http://localhost:8081/api/products/updateProduct",productCreationRequest, String.class);
        return productCreationResponse;
    }

    public HttpEntity<String> getAllProducts(){
        ResponseEntity<String> productCreationResponse = restTemplate.getForEntity("http://localhost:8081/api/products/", String.class);
        return productCreationResponse;
    }

    public HttpEntity<String> getProduct(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<String> productCreationResponse = restTemplate.getForEntity("http://localhost:8081/api/products/{id}", String.class, params);
        return productCreationResponse;
    }

    public String deleteProduct(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        restTemplate.delete("http://localhost:8081/api/products/{id}", params);
        return "Product Deleted, Product Id: " + id;
    }

    //Order Service
    public HttpEntity<String> getOrdersByUsersId(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<String> productCreationResponse = restTemplate.getForEntity("http://localhost:8183/api/orders/getOrderByUserId/{id}", String.class, params);
        return productCreationResponse;
    }


    public ResponseEntity<String> createOrder(OrderRequest orderRequest1,Integer userId){

        var orderRequest = new OrderRequest();
        orderRequest.setUserId(userId);
        orderRequest.setProductId(orderRequest1.getProductId());
        orderRequest.setQuantity(orderRequest1.getQuantity());
        ResponseEntity<String> orderCreationResponse = restTemplate.postForEntity("http://localhost:8183/api/orders/",orderRequest, String.class);
        return orderCreationResponse;
    }

    public String deleteOrder(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        restTemplate.delete("http://localhost:8183/api/orders/{id}", params);
        return "Product Deleted, Product Id: " + id;
    }

    public ResponseEntity<String> updateOrder(OrderRequest orderRequest1,Integer userId){

        var orderRequest = new OrderRequest();
        orderRequest.setUserId(userId);
        orderRequest.setProductId(orderRequest1.getProductId());
        orderRequest.setQuantity(orderRequest1.getQuantity());
        orderRequest.setId(orderRequest1.getId());
        ResponseEntity<String> orderCreationResponse = restTemplate.postForEntity("http://localhost:8183/api/orders/updateOrder",orderRequest, String.class);
        return orderCreationResponse;
    }

    public HttpEntity<String> getOrder(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<String> getOrder = restTemplate.getForEntity("http://localhost:8183/api/orders/{id}", String.class, params);
        return getOrder;
    }



}
