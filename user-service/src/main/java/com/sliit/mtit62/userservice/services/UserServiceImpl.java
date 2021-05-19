package com.sliit.mtit62.userservice.services;
import com.sliit.mtit62.userservice.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    /*
     *Product Service
     */

    //Create a Product
    public ResponseEntity<String> createProduct(UserRequest userRequest){

        var productCreationRequest = new ProductCreationRequest();
        productCreationRequest.setName(userRequest.getName());
        productCreationRequest.setPrice(userRequest.getPrice());
        productCreationRequest.setDescription(userRequest.getDescription());
        ResponseEntity<String> productCreationResponse = restTemplate.postForEntity("http://localhost:8081/api/products/",productCreationRequest, String.class);
        return productCreationResponse;
    }

    //Update a Product
    public ResponseEntity<String> updateProduct(UserRequest userRequest){

        var productCreationRequest = new ProductCreationRequest();
        productCreationRequest.setName(userRequest.getName());
        productCreationRequest.setPrice(userRequest.getPrice());
        productCreationRequest.setDescription(userRequest.getDescription());
        productCreationRequest.setId(userRequest.getId());
        ResponseEntity<String> productCreationResponse = restTemplate.postForEntity("http://localhost:8081/api/products/updateProduct",productCreationRequest, String.class);
        return productCreationResponse;
    }

    //Get all Products
    public HttpEntity<String> getAllProducts(){
        ResponseEntity<String> productCreationResponse = restTemplate.getForEntity("http://localhost:8081/api/products/", String.class);
        return productCreationResponse;
    }

    //Get a Product
    public HttpEntity<String> getProduct(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<String> productCreationResponse = restTemplate.getForEntity("http://localhost:8081/api/products/{id}", String.class, params);
        return productCreationResponse;
    }

    //Delete a Product
    public String deleteProduct(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        restTemplate.delete("http://localhost:8081/api/products/{id}", params);
        return "Product Deleted, Product Id: " + id;
    }

    /*
     *Order Service
     */

    //Get Orders by User ID
    public HttpEntity<String> getOrdersByUsersId(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<String> productCreationResponse = restTemplate.getForEntity("http://localhost:8183/api/orders/getOrderByUserId/{id}", String.class, params);
        return productCreationResponse;
    }


    //Create a Order
    public ResponseEntity<String> createOrder(OrderRequest orderRequest1,Integer userId){
        var orderRequest = new OrderRequest();
        orderRequest.setUserId(userId);
        orderRequest.setProductId(orderRequest1.getProductId());
        orderRequest.setQuantity(orderRequest1.getQuantity());
        ResponseEntity<String> orderCreationResponse = restTemplate.postForEntity("http://localhost:8183/api/orders/",orderRequest, String.class);
        return orderCreationResponse;
    }

    //Delete a Order
    public String deleteOrder(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        restTemplate.delete("http://localhost:8183/api/orders/{id}", params);
        return "Order Deleted, Order Id: " + id;
    }

    //Update a Order
    public ResponseEntity<String> updateOrder(OrderRequest orderRequest1,Integer userId){
        var orderRequest = new OrderRequest();
        orderRequest.setUserId(userId);
        orderRequest.setProductId(orderRequest1.getProductId());
        orderRequest.setQuantity(orderRequest1.getQuantity());
        orderRequest.setId(orderRequest1.getId());
        orderRequest.setTotalPrice(orderRequest1.getTotalPrice());
        orderRequest.setPaymentStatus(orderRequest1.getPaymentStatus());
        orderRequest.setDeliveryStatus(orderRequest1.getDeliveryStatus());
        ResponseEntity<String> orderCreationResponse = restTemplate.postForEntity("http://localhost:8183/api/orders/updateOrder",orderRequest, String.class);
        return orderCreationResponse;
    }

    //Get a Order
    public HttpEntity<String> getOrder(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<String> getOrder = restTemplate.getForEntity("http://localhost:8183/api/orders/{id}", String.class, params);
        return getOrder;
    }

    //Do payment
    public ResponseEntity<String> createPayment(AccountRequest accountRequest){
        ResponseEntity<String> orderCreationResponse = restTemplate.postForEntity("http://localhost:8184/api/accounts/",accountRequest, String.class);
        return orderCreationResponse;
    }

    //Get payments by order id
    public HttpEntity<AccountRequest> getPaymentsByOrderId(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<AccountRequest> getPayment = restTemplate.getForEntity("http://localhost:8184/api/accounts/payOrder/{id}", AccountRequest.class, params);
        return getPayment;
    }

    //Get Order Id's By user Id
    public HttpEntity<List> getOrderIdByUserId(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<List> getOrderId = restTemplate.getForEntity("http://localhost:8183/api/orders/getOrderIdByUserId/{id}", List.class, params);
        return getOrderId;
    }

    //Get Paid & Not delivered Orders
    public HttpEntity<String> getPaidNotDeliveredOrders(){
        ResponseEntity<String> getOrder = restTemplate.getForEntity("http://localhost:8182/api/deliveries/getNotDeliveredOrders", String.class);
        return getOrder;
    }

    //Assign Paid Orders To delivery Persons
    public ResponseEntity<String> assignOrdersToDeliveryPerson(OrderRequest orderRequest){
        ResponseEntity<String> orderCreationResponse = restTemplate.postForEntity("http://localhost:8182/api/deliveries/assignOrderToDeliveryPerson",orderRequest, String.class);
        return orderCreationResponse;
    }

    //Update Order status to delivered
    public ResponseEntity<String> updateOrderStatus(OrderRequest orderRequest){
        ResponseEntity<String> orderStatusUpdateResponse = restTemplate.postForEntity("http://localhost:8182/api/deliveries/updateOrderStatus",orderRequest, String.class);
        return orderStatusUpdateResponse;
    }

    //Get assigned orders of logged delivery person
    public HttpEntity<String> getAssignedOrders(Integer id){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<String> getOrder = restTemplate.getForEntity("http://localhost:8182/api/deliveries/getNotDeliveredOrders/{id}", String.class, params);
        return getOrder;
    }

}
