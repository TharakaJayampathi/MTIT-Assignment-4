package com.sliit.mtit62.userservice.controller;
import com.sliit.mtit62.userservice.dto.AccountRequest;
import com.sliit.mtit62.userservice.dto.OrderRequest;
import com.sliit.mtit62.userservice.dto.UserRequest;
import com.sliit.mtit62.userservice.models.User;
import com.sliit.mtit62.userservice.repository.UserRepository;
import com.sliit.mtit62.userservice.security.jwt.JwtUtils;
import com.sliit.mtit62.userservice.security.services.UserDetailsServiceImpl;
import com.sliit.mtit62.userservice.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //Get current logged user's Id
    public Integer getUserId(HttpServletRequest request){
        String jwt = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return user.getId().intValue();
    }

    /*
    *Product Service
    */

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
    public @ResponseBody HttpEntity<String> getAllProducts(){
        return userService.getAllProducts();
    }

    //Get Product by ID
    @GetMapping("/getProduct/{id}")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    HttpEntity<String> getProductById(@PathVariable Integer id){
        return userService.getProduct(id);
    }

    /*
     *Order Service
     */

    //Get Orders by User ID
    @GetMapping("/getOrdersByUserId")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    HttpEntity<String> getOrders(HttpServletRequest request){
        Integer userId = getUserId(request);
        return userService.getOrdersByUsersId(userId);
    }

    //Add Orders
    @PostMapping(value = "/addOrder")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<String> createOrder(@RequestBody OrderRequest request, HttpServletRequest request1) {
        Integer userId = getUserId(request1);
        return userService.createOrder(request,userId);
    }

    //Delete Order
    @DeleteMapping(value = "/deleteOrder/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public  @ResponseBody String deleteOrder(@PathVariable Integer id) { return userService.deleteOrder(id); }

    //Update Order
    @PostMapping(value = "/updateOrder")
    public @ResponseBody ResponseEntity<String> updateOrder(@RequestBody OrderRequest request, HttpServletRequest request1){
        Integer userId = getUserId(request1);
        return userService.updateOrder(request,userId);
    }

    //Get Order by ID
    @GetMapping("/getOrder/{id}")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    HttpEntity<String> getOrderById(@PathVariable Integer id){
        return userService.getOrder(id);
    }

    //Do a payment
    @PostMapping("/payment")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    ResponseEntity<String> createPayment(@RequestBody AccountRequest accountRequest){

        return userService.createPayment(accountRequest);
    }

    //Get Payment history by user
    @GetMapping("/getPaymentHistory")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    List<Object> getPaymentHistory(HttpServletRequest request){
        Integer userId = getUserId(request);
        HttpEntity<List> userIds = userService.getOrderIdByUserId(userId);

        List<Object> paymentHistory = new ArrayList<>();

        for(Object id : userIds.getBody())
        {
            paymentHistory.add(userService.getPaymentsByOrderId((Integer) id).getBody());
        }

        return paymentHistory;
    }

    //Get paid Not Delivered Orders
    @GetMapping("/getNotDeliveredOrders")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    HttpEntity<String> getNotDeliveredOrders(){
        return userService.getPaidNotDeliveredOrders();
    }

    //Assign Paid Orders To delivery Persons
    @PostMapping("/assignOrderToDeliveryPerson")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    ResponseEntity<String> assignOrdersToDeliveryPerson(@RequestBody OrderRequest orderRequest){
        return userService.assignOrdersToDeliveryPerson(orderRequest);
    }

    @PostMapping("/updateOrderStatus")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    ResponseEntity<String> updateOrderStatus(@RequestBody OrderRequest orderRequest){
        return userService.updateOrderStatus(orderRequest);
    }

    //Get assigned orders of logged delivery person
    @GetMapping("/getMyAssignedOrders")
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    HttpEntity<String> getAssignedOrders(HttpServletRequest request){
        Integer userId = getUserId(request);
        return userService.getAssignedOrders(userId);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}
