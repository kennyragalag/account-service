package com.example.accountservice.repository;

import com.example.accountservice.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    @EntityGraph(attributePaths = {"accounts"})
    Optional<Customer> findByCustomerNumber(Long customerNumber);
    
}
