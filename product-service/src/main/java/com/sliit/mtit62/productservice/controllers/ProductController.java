package com.sliit.mtit62.productservice.controllers;

import com.sliit.mtit62.productservice.persistence.ProductsRepository;
import com.sliit.mtit62.productservice.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping(value = "/{id}")
    public  @ResponseBody  String deleteProduct(@PathVariable Integer id) {
        if (productsRepository.existsById(id)) {
            productsRepository.deleteById(id);
            return "Product deleted successfully";
        }else{
            return "Product does not exists in the database.";
        }
    }

    @PutMapping(value = "/")
    public String update(@RequestBody Products Products) {
        if (productsRepository.existsById(Products.getId())) {
            try {
                productsRepository.save(Products);
                return Products.getName() + " updated successfully";
            } catch (Exception e) {
                throw e;
            }
        } else {
            return "Product does not exists in the database.";
        }
    }

}
