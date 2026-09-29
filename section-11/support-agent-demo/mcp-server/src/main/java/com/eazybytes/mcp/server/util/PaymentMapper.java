package com.eazybytes.mcp.server.util;

import com.eazybytes.mcp.server.domain.entity.Payment;
import com.eazybytes.mcp.server.dto.PaymentInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentInfo toPaymentInfo(Payment payment);
}