package com.eazybytes.mcp.server.util;

import com.eazybytes.mcp.server.domain.entity.Customer;
import com.eazybytes.mcp.server.dto.CustomerInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    
    CustomerInfo toCustomerInfo(Customer customer);
}