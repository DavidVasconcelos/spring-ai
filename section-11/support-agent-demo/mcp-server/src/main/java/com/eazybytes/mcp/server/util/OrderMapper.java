package com.eazybytes.mcp.server.util;

import com.eazybytes.mcp.server.domain.entity.CustomerOrder;
import com.eazybytes.mcp.server.domain.entity.OrderItem;
import com.eazybytes.mcp.server.dto.OrderDetails;
import com.eazybytes.mcp.server.dto.OrderItemInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PaymentMapper.class})
public interface OrderMapper {

  @Mapping(source = "customer.fullName", target = "customerName")
  @Mapping(source = "customer.email", target = "customerEmail")
  OrderDetails toOrderDetails(CustomerOrder order);

  @Mapping(source = "product.sku", target = "sku")
  @Mapping(source = "product.name", target = "productName")
  OrderItemInfo toOrderItemInfo(OrderItem item);

}