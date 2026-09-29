package com.eazybytes.mcp.server.domain.entity;

import com.eazybytes.mcp.server.domain.enumerator.RefundStatus;
import com.eazybytes.mcp.server.domain.enumerator.RefundType;
import jakarta.persistence.Column;
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
import java.util.UUID;
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
@Table(name = "refunds")
public class Refund {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default
  @Column(name = "refund_number", nullable = false, updatable = false, unique = true)
  private String refundNumber = UUID.randomUUID().toString();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private CustomerOrder order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_id")
  private Payment payment;

  private BigDecimal amount;

  private String currency;

  private String reason;

  @Enumerated(EnumType.STRING)
  private RefundType refundType;

  @Enumerated(EnumType.STRING)
  private RefundStatus status;

  @Column(insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Refund refund)) {
      return false;
    }
    return Objects.equals(refundNumber, refund.refundNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(refundNumber);
  }
}