package com.sliit.mtit62.productservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String Name;
    private Float Price;
    private String Description;

    public  Products(){

    }

    public Products(int id, String name, float price, String description) {
        this.Id = id;
        this.Name = name;
        this.Price = price;
        this.Description = description;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return  "Product Id = " + this.Id + ", " +
                "product Name = " + this.Name + ", " +
                "Price = " + this.Price + ", " +
                "Description = " + this.Description;
    }
}
