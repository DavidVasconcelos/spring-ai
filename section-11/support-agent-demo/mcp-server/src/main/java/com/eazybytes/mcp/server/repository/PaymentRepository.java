package com.eazybytes.mcp.server.repository;

import com.eazybytes.mcp.server.domain.entity.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByOrderOrderNumberOrderByChargedAtAsc(String orderNumber);

    Optional<Payment> findByTransactionRef(String transactionRef);
}