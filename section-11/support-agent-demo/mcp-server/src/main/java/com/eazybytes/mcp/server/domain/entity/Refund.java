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
      return Objects.equals(id, refund.id) && Objects.equals(order, refund.order)
            && Objects.equals(payment, refund.payment) && Objects.equals(amount,
            refund.amount) && Objects.equals(currency, refund.currency)
            && Objects.equals(reason, refund.reason) && refundType == refund.refundType
            && status == refund.status && Objects.equals(createdAt, refund.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, payment, amount, currency, reason, refundType, status,
            createdAt);
    }
}