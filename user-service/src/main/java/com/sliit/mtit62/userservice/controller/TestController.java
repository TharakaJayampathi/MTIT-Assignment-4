package com.sliit.mtit62.userservice.controller;
import com.sliit.mtit62.userservice.dto.UserRequest;
import com.sliit.mtit62.userservice.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class TestController {
    @Autowired
    private UserServiceImpl userService;

    //Add Products
    @PostMapping(value = "/addProducts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<String> createProduct(@RequestBody UserRequest request) {
        return userService.createProduct(request);
    }

    //Update Products
    @PostMapping(value = "/updateProduct")
    public @ResponseBody ResponseEntity<String> updateProduct(@RequestBody UserRequest request){
        return userService.updateProduct(request);
    }

    //Delete Products
    @DeleteMapping(value = "/deleteProducts/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public  @ResponseBody String deleteProduct(@PathVariable Integer id) { return userService.deleteProduct(id); }

    //Get All Products
    @GetMapping("/getAllProducts")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody HttpEntity<String> get(){ return userService.getAllProducts(); }

    //Get Product by ID
    @GetMapping("/getProduct/{id}")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody HttpEntity<String> get(@PathVariable Integer id){ return userService.getProduct(id); }



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
