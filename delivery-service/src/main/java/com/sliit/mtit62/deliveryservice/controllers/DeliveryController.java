package com.sliit.mtit62.deliveryservice.controllers;

import com.sliit.mtit62.deliveryservice.dto.DeliveryResponse;
import com.sliit.mtit62.deliveryservice.dto.OrderRequest;
import com.sliit.mtit62.deliveryservice.persistence.DeliveriesRepository;
import com.sliit.mtit62.deliveryservice.models.Deliveries;
import com.sliit.mtit62.deliveryservice.services.DeliveryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/deliveries",produces = "application/json")
public class DeliveryController {

    @Autowired
    private DeliveriesRepository deliveriesRepository;

    @Autowired
    private DeliveryServiceImpl deliveryService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/getNotDeliveredOrders", method = RequestMethod.GET)
    public @ResponseBody Object getNotDeliveredOrders(){
        return deliveryService.getAllPaidNotDeliveredOrders();
    }

    @Transactional
    @RequestMapping(value = "/assignOrderToDeliveryPerson", method = RequestMethod.POST)
    public @ResponseBody
    String assignOrderToDeliveryPerson(@RequestBody Deliveries deliveries) {

        // connect with order service

        Deliveries deliveries1 = new Deliveries();
        deliveries1.setUserId(deliveries.getUserId());
        deliveries1.setOrderId(deliveries.getOrderId());
        deliveries1.setDeliveryStatus("ReadyToDeliver");
        deliveriesRepository.save(deliveries1);
        return "Order "+ deliveries.getOrderId() + " Assigned to Delivery Person Id " + deliveries.getUserId();
    }

    @RequestMapping(value = "/getNotDeliveredOrders/{id}", method = RequestMethod.GET)
    public @ResponseBody Object getDeliveryPersonOrders(@PathVariable Integer id){

        List<Deliveries> deliveries = jdbcTemplate.query("SELECT * FROM deliveries WHERE user_id="+id, (resultSet, rowNum) -> new Deliveries(
                resultSet.getInt("id"),
                resultSet.getInt("order_id"),
                resultSet.getInt("user_id"),
                resultSet.getString("delivery_status")
        ));

        List<Object> DeliveryResponse = new ArrayList<>();


               // connect with order service

        for(Deliveries delivery : deliveries){
            DeliveryResponse deliveryResponse = new DeliveryResponse();
            OrderRequest orderRequest = deliveryService.getOrdersById(delivery.getOrderId()).getBody();

            deliveryResponse.setId(delivery.getId());
            deliveryResponse.setOrderId(delivery.getOrderId());
            deliveryResponse.setUserId(delivery.getUserId());
            deliveryResponse.setTotalPrice(orderRequest.getTotalPrice());
            deliveryResponse.setProductId(orderRequest.getProductId());
            deliveryResponse.setPaymentStatus(orderRequest.getPaymentStatus());
            deliveryResponse.setDeliveryStatus(orderRequest.getDeliveryStatus());
            deliveryResponse.setOrderId(delivery.getOrderId());
            DeliveryResponse.add(deliveryResponse);
        }

        return DeliveryResponse;
    }


    @DeleteMapping(value = "/{id}")
    public  @ResponseBody  String deleteDelivery(@PathVariable Integer id) {
        if (deliveriesRepository.existsById(id)) {
            deliveriesRepository.deleteById(id);
            return "Product deleted successfully";
        }else{
            return "Product does not exists in the database.";
        }
    }

    @PostMapping(value = "/updateOrderStatus")
    public String update(@RequestBody Deliveries Deliveries) {
        OrderRequest orderRequest = deliveryService.getOrdersById(Deliveries.getOrderId()).getBody();

        OrderRequest updatedOrderRequest = new OrderRequest();

        updatedOrderRequest.setDeliveryStatus("Delivered");
        updatedOrderRequest.setQuantity(orderRequest.getQuantity());
        updatedOrderRequest.setPaymentStatus(orderRequest.getPaymentStatus());
        updatedOrderRequest.setProductId(orderRequest.getProductId());
        updatedOrderRequest.setId(orderRequest.getId());
        updatedOrderRequest.setUserId(orderRequest.getUserId());
        updatedOrderRequest.setTotalPrice(orderRequest.getTotalPrice());

        deliveryService.updateOrder(updatedOrderRequest);

       // return deliveryService.updateOrder(updatedOrderRequest);

        return "Order Status Updated. Order Delivered to the Customer. : D";
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}

