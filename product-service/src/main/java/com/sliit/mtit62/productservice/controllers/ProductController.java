package com.sliit.mtit62.productservice.controllers;

import com.sliit.mtit62.productservice.persistence.ProductsRepository;
import com.sliit.mtit62.productservice.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/products",produces = "application/json")
public class ProductController {

     @Autowired
     private ProductsRepository productsRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody Iterable<Products> get(){
        return productsRepository.findAll();
    }

    @Transactional
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody Iterable<Products> productReq(@RequestBody Products products) {
        productsRepository.save(products);
        return productsRepository.findAll();
    }



}
