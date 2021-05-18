package com.sliit.mtit62.accountservice.services;


import com.sliit.mtit62.accountservice.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    //Get a Order
    public HttpEntity<OrderRequest> getOrder(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<OrderRequest> productCreationResponse = restTemplate.getForEntity("http://localhost:8183/api/orders/{id}", OrderRequest.class, params);
        return productCreationResponse;
    }

    //Update a order
    public ResponseEntity<String> updateOrder(OrderRequest orderRequest1,Integer userId){
        var orderRequest = new OrderRequest();
        orderRequest.setUserId(userId);
        orderRequest.setProductId(orderRequest1.getProductId());
        orderRequest.setQuantity(orderRequest1.getQuantity());
        orderRequest.setId(orderRequest1.getId());
        orderRequest.setTotalPrice(orderRequest1.getTotalPrice());
        orderRequest.setPaymentStatus("Paid");
        orderRequest.setDeliveryStatus(orderRequest1.getDeliveryStatus());
        ResponseEntity<String> orderCreationResponse = restTemplate.postForEntity("http://localhost:8183/api/orders/updateOrder",orderRequest, String.class);
        return orderCreationResponse;
    }
}
