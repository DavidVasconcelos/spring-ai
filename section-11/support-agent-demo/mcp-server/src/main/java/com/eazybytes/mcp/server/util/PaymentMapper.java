package com.eazybytes.mcp.server.util;

import com.eazybytes.mcp.server.domain.entity.Payment;
import com.eazybytes.mcp.server.dto.PaymentInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "paymentMethod", target = "method")
    PaymentInfo toPaymentInfo(Payment payment);
}