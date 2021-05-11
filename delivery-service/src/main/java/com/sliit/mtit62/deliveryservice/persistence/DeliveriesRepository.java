package com.sliit.mtit62.deliveryservice.persistence;

import com.sliit.mtit62.deliveryservice.models.Deliveries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeliveriesRepository extends CrudRepository<Deliveries, Integer> {

}
