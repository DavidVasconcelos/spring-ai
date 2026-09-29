package com.eazybytes.mcp.server.util;

import com.eazybytes.mcp.server.domain.entity.Product;
import com.eazybytes.mcp.server.dto.ProductInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductInfo toProductInfo(Product product);
}