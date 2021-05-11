package com.sliit.mtit62.deliveryservice.controllers;

import com.sliit.mtit62.deliveryservice.persistence.DeliveriesRepository;
import com.sliit.mtit62.deliveryservice.models.Deliveries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/deliveries",produces = "application/json")
public class DeliveryController {

    @Autowired
    private DeliveriesRepository deliveriesRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody Iterable<Deliveries> get(){
        return deliveriesRepository.findAll();
    }

    @Transactional
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody Iterable<Deliveries> deliveryReq(@RequestBody Deliveries deliveries) {
        deliveriesRepository.save(deliveries);
        return deliveriesRepository.findAll();
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

    @PutMapping(value = "/")
    public String update(@RequestBody Deliveries Deliveries) {
        if (deliveriesRepository.existsById(Deliveries.getId())) {
            try {
                deliveriesRepository.save(Deliveries);
                return Deliveries.getName() + " updated successfully";
            } catch (Exception e) {
                throw e;
            }
        } else {
            return "Product does not exists in the database.";
        }
    }

}

