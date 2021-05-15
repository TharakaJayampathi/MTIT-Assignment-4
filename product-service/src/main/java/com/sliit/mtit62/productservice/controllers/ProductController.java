package com.sliit.mtit62.productservice.controllers;

import com.sliit.mtit62.productservice.persistence.ProductsRepository;
import com.sliit.mtit62.productservice.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products",produces = "application/json")
public class ProductController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @GetMapping(value = "/{id}")
    public @ResponseBody
    Object productReq(@PathVariable Integer id) {

        List<Products> products = jdbcTemplate.query("SELECT * FROM products WHERE id="+id, (resultSet, rowNum) -> new Products(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getFloat("price"),
                resultSet.getString("description")
        ));

        if(productsRepository.existsById(id)){
            return products;
        }else{
            return "Product does not exists in the database.";
        }

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

    @PostMapping(value = "/updateProduct")
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
