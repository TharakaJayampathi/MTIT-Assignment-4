package com.sliit.mtit62.productservice.persistence;
import com.sliit.mtit62.productservice.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {

}
