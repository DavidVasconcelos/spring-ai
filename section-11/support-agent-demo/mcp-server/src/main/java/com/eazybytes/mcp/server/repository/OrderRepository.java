package com.eazybytes.mcp.server.repository;

import com.eazybytes.mcp.server.domain.entity.CustomerOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

    Optional<CustomerOrder> findByOrderNumber(String orderNumber);

    List<CustomerOrder> findByCustomerEmailIgnoreCaseOrderByOrderDateDesc(String email);
}