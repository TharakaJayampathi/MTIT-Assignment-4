package com.sliit.mtit62.productservice.persistence;
import com.sliit.mtit62.productservice.models.Products;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductsRepository extends CrudRepository<Products, Integer> {

}
