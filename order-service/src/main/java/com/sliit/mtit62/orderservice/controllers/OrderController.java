package com.sliit.mtit62.orderservice.controllers;

import com.sliit.mtit62.orderservice.models.Orders;
import com.sliit.mtit62.orderservice.persistence.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/orders",produces = "application/json")
public class OrderController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping(value = "/getOrderByUserId/{id}")
    public @ResponseBody
    Object getOrderByUserId(@PathVariable Integer id){
        List<Orders> orders = jdbcTemplate.query("SELECT * FROM orders WHERE user_id="+id, (resultSet, rowNum) -> new Orders(
                resultSet.getInt("id"),
                resultSet.getInt("product_id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("quantity"),
                resultSet.getFloat("total_price")
        ));
        if(orders.isEmpty()){
            return "No orders from user id: "+id;
        }else {
            return orders;
        }

    }

    @Transactional
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody Iterable<Orders> orderReq(@RequestBody Orders orders) {
        ordersRepository.save(orders);
        return ordersRepository.findAll();
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

    @PutMapping(value = "/")
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

}

