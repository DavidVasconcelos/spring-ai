package com.eazybytes.mcp.server.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
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
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private BigDecimal unitPrice;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OrderItem orderItem)) {
            return false;
        }
      return Objects.equals(id, orderItem.id) && Objects.equals(order,
            orderItem.order) && Objects.equals(product, orderItem.product)
            && Objects.equals(quantity, orderItem.quantity) && Objects.equals(
            unitPrice, orderItem.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, product, quantity, unitPrice);
    }
}