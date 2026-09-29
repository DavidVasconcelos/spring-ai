package com.eazybytes.support.agent.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Request body for {@code PUT /api/v1/messages}.
 */
public record ReadUpdate(
    @JsonProperty("IDs") List<String> ids,
    @JsonProperty("Read") boolean read
) {

}