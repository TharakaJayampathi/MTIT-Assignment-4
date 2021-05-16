package com.sliit.mtit62.orderservice.servises;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    public HttpEntity<String> getProduct(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<String> productCreationResponse = restTemplate.getForEntity("http://localhost:8081/api/products/{id}", String.class, params);
        return productCreationResponse;
    }
}
