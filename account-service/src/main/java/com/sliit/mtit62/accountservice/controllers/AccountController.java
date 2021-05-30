package com.sliit.mtit62.accountservice.controllers;

import com.sliit.mtit62.accountservice.dto.OrderRequest;
import com.sliit.mtit62.accountservice.persistence.AccountsRepository;
import com.sliit.mtit62.accountservice.models.Accounts;
import com.sliit.mtit62.accountservice.services.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(value = "/api/accounts",produces = "application/json")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Accounts> get(){
        return accountsRepository.findAll();
    }

    @Transactional
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody
    Object accountReq(@RequestBody Accounts accounts) {

        // call to order service
        HttpEntity<OrderRequest> currentOrder = accountService.getOrder(accounts.getOrderId());

        // Imp payment validation logic
        if(accounts.getAmount() < currentOrder.getBody().getTotalPrice()){
            return "Payment not enough. Order price: "+ currentOrder.getBody().getTotalPrice();
        }
        if(accounts.getAmount() > currentOrder.getBody().getTotalPrice()){
            return "Payment Rejected. Order price: "+ currentOrder.getBody().getTotalPrice();
        }
        if(currentOrder.getBody().getPaymentStatus().equals("Paid")){
            return "You already paid";
        }

        // got order service and update payment status
        accountService.updateOrder(currentOrder.getBody(),currentOrder.getBody().getUserId());

        accountsRepository.save(accounts);
        return "Payment Successful. You paid "+ accounts.getAmount() + ". Order Id:" + accounts.getOrderId();
    }

    @DeleteMapping(value = "/{id}")
    public  @ResponseBody  String deleteAccount(@PathVariable Integer id) {
        if (accountsRepository.existsById(id)) {
            accountsRepository.deleteById(id);
            return "Product deleted successfully";
        }else{
            return "Product does not exists in the database.";
        }
    }

    @GetMapping(value = "/payOrder/{id}")
    public @ResponseBody
    Object getOrder(@PathVariable Integer id){

        List<Accounts> accounts = jdbcTemplate.query("SELECT * FROM accounts WHERE order_id="+id, (resultSet, rowNum) -> new Accounts(
                resultSet.getInt("id"),
                resultSet.getString("type"),
                resultSet.getInt("order_id"),
                resultSet.getFloat("amount"),
                resultSet.getString("description")

        ));

//        Accounts accountsDetails = new Accounts();
//        accountsDetails.setId(accounts.get(0).getId());
//        accountsDetails.setType(accounts.get(0).getType());
//        accountsDetails.setOrderId(accounts.get(0).getOrderId());
//        accountsDetails.setAmount(accounts.get(0).getAmount());
//        accountsDetails.setDescription(accounts.get(0).getDescription());

        return accounts.get(0);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}


