package com.eazybytes.mcp.server.repository;

import com.eazybytes.mcp.server.domain.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund, Long> {
}