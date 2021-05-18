package com.sliit.mtit62.deliveryservice.services;

import com.sliit.mtit62.deliveryservice.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeliveryServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<Object> getAllPaidNotDeliveredOrders(){
        ResponseEntity<Object> getAllPaidNotDeliveredOrders = restTemplate.getForEntity("http://localhost:8183/api/orders/getAllPaidOrders",Object.class );
        return getAllPaidNotDeliveredOrders;
    }

    public ResponseEntity<OrderRequest> getOrdersById(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<OrderRequest> getAllPaidNotDeliveredOrders = restTemplate.getForEntity("http://localhost:8183/api/orders/{id}",OrderRequest.class, params );
        return getAllPaidNotDeliveredOrders;
    }

    public ResponseEntity<Object> updateOrder(OrderRequest orderRequest){
        ResponseEntity<Object> getAllPaidNotDeliveredOrders = restTemplate.postForEntity("http://localhost:8183/api/orders/updateOrder",orderRequest, Object.class  );
        return getAllPaidNotDeliveredOrders;
    }
}
