package com.eazybytes.mcp.server.domain.entity;

import com.eazybytes.mcp.server.domain.enumerator.OrderStatus;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Maps the {@code orders} table. Named {@code CustomerOrder} because {@code Order} is an SQL
 * reserved word and an unhelpfully generic type name.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class CustomerOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String orderNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  private LocalDate orderDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  private String shippingAddress;

  private BigDecimal totalAmount;

  private String currency;

  @Builder.Default
  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
  private List<OrderItem> items = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
  private List<Payment> payments = new ArrayList<>();

  @Column(insertable = false, updatable = false)
  private LocalDateTime createdAt;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof CustomerOrder that)) {
      return false;
    }
    return Objects.equals(orderNumber, that.orderNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(orderNumber);
  }
}