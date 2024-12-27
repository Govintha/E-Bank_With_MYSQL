package com.easybank.accounts.repository;

import com.easybank.accounts.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
   This is Repo response for DB opration
   @Repository on interface behind the scene JPA framework willl create a bean
   implementation of this is interface
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customers,Long> {

    Optional<Customers> findByMobileNumber(String mobileNumber);

}
