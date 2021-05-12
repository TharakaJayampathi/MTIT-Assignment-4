package com.sliit.mtit62.orderservice.persistence;

import com.sliit.mtit62.orderservice.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends CrudRepository<Orders, Integer> {

}
