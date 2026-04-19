package com.learning.orderservice.repository;

import com.learning.orderservice.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, String> {
}
