package com.sliit.mtit62.orderservice.controllers;

import com.sliit.mtit62.orderservice.models.Orders;
import com.sliit.mtit62.orderservice.persistence.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/orders",produces = "application/json")
public class OrderController {

    @Autowired
    private OrdersRepository ordersRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Orders> get(){
        return ordersRepository.findAll();
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
                return Orders.getName() + " updated successfully";
            } catch (Exception e) {
                throw e;
            }
        } else {
            return "Order does not exists in the database.";
        }
    }

}

