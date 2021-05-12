package com.sliit.mtit62.customerservice.controller;

import com.sliit.mtit62.customerservice.dto.CustomerRequest;
import com.sliit.mtit62.customerservice.dto.CustomerResponse;
import com.sliit.mtit62.customerservice.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class TestController {
    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping(value = "/addProducts")
    public @ResponseBody
    CustomerResponse createCustomer(@RequestBody CustomerRequest request) {
        System.out.println("Customer Details : " + request);
        return customerService.createCustomer(request);
    }

    @DeleteMapping(value = "/deleteProducts/{id}")
    public  @ResponseBody String deleteProduct(@PathVariable Integer id) { return customerService.deleteProduct(id); }

    @GetMapping("/getAllProducts")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    HttpEntity<String> get(){ return customerService.getAllProducts(); }


    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
