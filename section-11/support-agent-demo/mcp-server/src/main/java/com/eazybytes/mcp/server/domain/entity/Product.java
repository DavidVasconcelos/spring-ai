package com.eazybytes.mcp.server.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String sku;

  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  private String category;

  private BigDecimal price;

  private String currency;

  /**
   * Product-agnostic attribute bag stored as native MySQL JSON, surfaced here as the raw JSON
   * string so any product category (voltage, page count, apparel size, ...) flows through
   * untouched.
   */
  @Column(columnDefinition = "json")
  private String specifications;

  private Integer warrantyMonths;

  private Integer stockQuantity;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Product product)) {
      return false;
    }
    return Objects.equals(sku, product.sku);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(sku);
  }
}