package com.eazybytes.mcp.server.util;

import com.eazybytes.mcp.server.domain.entity.CustomerOrder;
import com.eazybytes.mcp.server.domain.entity.OrderItem;
import com.eazybytes.mcp.server.domain.entity.Payment;
import com.eazybytes.mcp.server.dto.OrderDetails;
import com.eazybytes.mcp.server.dto.OrderItemInfo;
import com.eazybytes.mcp.server.dto.PaymentInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PaymentMapper.class})
public interface OrderMapper {

  @Mapping(source = "customer.fullName", target = "fullName")
  @Mapping(source = "customer.email", target = "email")
  OrderDetails toOrderDetails(CustomerOrder order);

  @Mapping(source = "product.sku", target = "sku")
  @Mapping(source = "product.name", target = "name")
  OrderItemInfo toOrderItemInfo(OrderItem item);

}