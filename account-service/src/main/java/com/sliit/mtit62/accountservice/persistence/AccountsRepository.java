package com.sliit.mtit62.accountservice.persistence;

import com.sliit.mtit62.accountservice.models.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Integer> {

}
