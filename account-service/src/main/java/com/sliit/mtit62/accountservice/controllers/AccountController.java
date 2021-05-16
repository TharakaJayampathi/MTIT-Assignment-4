package com.sliit.mtit62.accountservice.controllers;

import com.sliit.mtit62.accountservice.persistence.AccountsRepository;
import com.sliit.mtit62.accountservice.models.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/accounts",produces = "application/json")
public class AccountController {

    @Autowired
    private AccountsRepository accountsRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Accounts> get(){
        return accountsRepository.findAll();
    }

    @Transactional
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody Iterable<Accounts> accountReq(@RequestBody Accounts accounts) {
        accountsRepository.save(accounts);
        return accountsRepository.findAll();
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

    @PutMapping(value = "/")
    public String update(@RequestBody Accounts Accounts) {
        if (accountsRepository.existsById(Accounts.getId())) {
            try {
                accountsRepository.save(Accounts);
                return Accounts.getName() + " updated successfully";
            } catch (Exception e) {
                throw e;
            }
        } else {
            return "Product does not exists in the database.";
        }
    }

}


