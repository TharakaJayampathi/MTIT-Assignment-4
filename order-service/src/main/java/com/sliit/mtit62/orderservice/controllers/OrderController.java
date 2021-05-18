package com.sliit.mtit62.orderservice.controllers;

import com.sliit.mtit62.orderservice.dto.OrderResponse;
import com.sliit.mtit62.orderservice.dto.ProductRequest;
import com.sliit.mtit62.orderservice.models.Orders;
import com.sliit.mtit62.orderservice.persistence.OrdersRepository;
import com.sliit.mtit62.orderservice.servises.OrderServiceImpl;
import groovy.json.JsonSlurper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/orders",produces = "application/json")
public class OrderController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping(value = "/getOrderByUserId/{id}")
    public @ResponseBody
    Object getOrderByUserId(@PathVariable Integer id){

        List<Orders> orders = jdbcTemplate.query("SELECT * FROM orders WHERE user_id="+id, (resultSet, rowNum) -> new Orders(
                resultSet.getInt("id"),
                resultSet.getInt("product_id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("quantity"),
                resultSet.getFloat("total_price"),
                resultSet.getString("payment_status"),
                resultSet.getString("delivery_status")
        ));

        List<Object> fullOrders = new ArrayList<>();


        for(Orders order1 : orders)
        {
            OrderResponse orderResponse = new OrderResponse();
            ProductRequest product = orderService.getProduct(order1.getProductId()).getBody();

            orderResponse.setId(order1.getId());
            orderResponse.setProductId(order1.getProductId());
            orderResponse.setUserId(order1.getUserId());
            orderResponse.setTotalPrice(order1.getTotalPrice());
            orderResponse.setPaymentStatus(order1.getPaymentStatus());
            orderResponse.setName(product.getName());
            orderResponse.setPrice(product.getPrice());
            orderResponse.setDescription(product.getDescription());
            orderResponse.setQuantity(order1.getQuantity());
            orderResponse.setDeliveryStatus(order1.getDeliveryStatus());
            fullOrders.add(orderResponse);

//            List<Object> order = new ArrayList<>();
//            Object product = orderService.getProduct(order1.getProductId()).getBody();
//            order.add(0,product);
//            order.add(1,order1);
//
//            fullOrders.add(order);
        }
        return fullOrders;
    }

    @GetMapping(value = "/getOrderIdByUserId/{id}")
    public @ResponseBody
    Object getOnlyOrdersByUserId(@PathVariable Integer id){

        List<Orders> orders = jdbcTemplate.query("SELECT * FROM orders WHERE user_id="+id, (resultSet, rowNum) -> new Orders(
                resultSet.getInt("id"),
                resultSet.getInt("product_id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("quantity"),
                resultSet.getFloat("total_price"),
                resultSet.getString("payment_status"),
                resultSet.getString("delivery_status")
        ));
        List<Object> userOrderIds = new ArrayList<>();
        for(Orders order1 : orders)
        {
            userOrderIds.add(order1.getId());
        }
        return userOrderIds;
    }

    @GetMapping(value = "/getAllPaidOrders")
    public @ResponseBody
    Object getAllPaidOrders(){
        List<Orders> orders = jdbcTemplate.query("SELECT * FROM orders WHERE payment_status = 'Paid' AND delivery_status = 'NotDelivered'", (resultSet, rowNum) -> new Orders(
                resultSet.getInt("id"),
                resultSet.getInt("product_id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("quantity"),
                resultSet.getFloat("total_price"),
                resultSet.getString("payment_status"),
                resultSet.getString("delivery_status")
        ));
        return orders;
    }

    @Transactional
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody
    String orderReq(@RequestBody Orders orders) {

        //Calculate Total Price
        Object productPrice = orderService.getProduct(orders.getProductId()).getBody().getPrice();
        orders.setTotalPrice((Float) productPrice * orders.getQuantity());
        orders.setPaymentStatus("Pending");
        orders.setDeliveryStatus("NotDelivered");
        ordersRepository.save(orders);
        return "Order Added Successfully. Order ID:" + orders.getId();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Object orderReq(@PathVariable Integer id) {
        if(ordersRepository.existsById(id)){
            return ordersRepository.findById(id);
        }else{
            return "Order does not exists in the database.";
        }
    }

    @DeleteMapping(value = "/{id}")
    public  @ResponseBody  String deleteOrder(@PathVariable Integer id) {
        if (ordersRepository.existsById(id)) {
            ordersRepository.deleteById(id);
            return "Order deleted successfully";
        }else{
            return "Order does not exists in the database.";
        }
    }

    @PostMapping(value = "/updateOrder")
    public String update(@RequestBody Orders Orders) {
        if (ordersRepository.existsById(Orders.getId())) {
            try {
                ordersRepository.save(Orders);
                return Orders.getId() + " updated successfully";
            } catch (Exception e) {
                throw e;
            }
        } else {
            return "Order does not exists in the database.";
        }
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}

