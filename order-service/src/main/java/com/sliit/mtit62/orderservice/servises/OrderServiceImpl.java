package com.sliit.mtit62.orderservice.servises;
import com.sliit.mtit62.orderservice.dto.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<ProductRequest> getProduct(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<ProductRequest> productCreationResponse = restTemplate.getForEntity("http://localhost:8081/api/products/{id}",ProductRequest.class , params);
        return productCreationResponse;
    }
}
