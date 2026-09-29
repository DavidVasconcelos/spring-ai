package com.eazybytes.mcp.server.dto;

public record CustomerInfo(
    Long id,
    String fullName,
    String email,
    String phone,
    String preferredLanguage,
    String loyaltyTier) {

}