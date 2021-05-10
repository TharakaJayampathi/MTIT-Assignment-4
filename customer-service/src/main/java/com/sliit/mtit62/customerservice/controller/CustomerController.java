package com.sliit.mtit62.customerservice.controller;
import com.sliit.mtit62.customerservice.dto.CustomerRequest;
import com.sliit.mtit62.customerservice.dto.CustomerResponse;
import com.sliit.mtit62.customerservice.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/products", produces = "application/json")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @RequestMapping(value = "/", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody CustomerResponse createCustomer(@RequestBody CustomerRequest request) {
        System.out.println("Customer Details : " + request);
        return customerService.createCustomer(request);
    }

    @RequestMapping(value = "/", consumes = "application/json", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody
    HttpEntity<String> get(){ return customerService.getAllProducts(); }

    @DeleteMapping(value = "/{id}")
    public  @ResponseBody String deleteProduct(@PathVariable Integer id) { return customerService.deleteProduct(id); }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
