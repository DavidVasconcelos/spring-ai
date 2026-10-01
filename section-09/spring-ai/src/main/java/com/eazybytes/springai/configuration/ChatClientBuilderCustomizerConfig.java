package com.eazybytes.springai.configuration;

import com.eazybytes.springai.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClientBuilderCustomizer;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class ChatClientBuilderCustomizerConfig {

  @Bean
  ChatClientBuilderCustomizer loggerAdvisor() {
    return builder -> builder.defaultAdvisors(new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE));
  }

  @Bean
  @ConditionalOnProperty(name = "audit.token-usage.enabled", havingValue = "true")
  ChatClientBuilderCustomizer auditAdvisor() {
    return builder -> builder.defaultAdvisors(new TokenUsageAuditAdvisor());
  }
}
