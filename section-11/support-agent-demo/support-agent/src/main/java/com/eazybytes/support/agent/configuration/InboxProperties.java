package com.eazybytes.support.agent.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "support-agent.inbox")
public record InboxProperties(String baseUrl,
                              String address,
                              long pollInterval,
                              int batchSize) {
}
