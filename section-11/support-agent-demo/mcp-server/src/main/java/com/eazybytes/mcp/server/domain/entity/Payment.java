package com.eazybytes.mcp.server.domain.entity;

import com.eazybytes.mcp.server.domain.enumerator.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private CustomerOrder order;

  private BigDecimal amount;

  private String currency;

  private String paymentMethod;

  private String transactionRef;

  @Enumerated(EnumType.STRING)
  private PaymentStatus status;

  private LocalDateTime chargedAt;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Payment payment)) {
      return false;
    }
    return Objects.equals(transactionRef, payment.transactionRef);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(transactionRef);
  }
}