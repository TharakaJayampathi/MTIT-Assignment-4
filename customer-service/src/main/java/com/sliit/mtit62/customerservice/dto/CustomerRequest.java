package com.sliit.mtit62.customerservice.dto;

public class CustomerRequest {

    private Integer Id;
    private String Name;
    private Float Price;
    private String Description;

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
        return "Product Request{" +
                "Product Name='" + Name + '\'' + '}';
    }
}
