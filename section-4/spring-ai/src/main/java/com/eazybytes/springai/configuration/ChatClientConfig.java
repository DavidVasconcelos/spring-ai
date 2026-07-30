package com.eazybytes.springai.configuration;

import com.eazybytes.springai.advisor.TokenUsageAuditAdvisor;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

  private static final String DEFAULT_USER = "How can you help me?";
  private static final List<String> SENSITIVE_WORDS = List.of("Shakira", "Madonna", "Argentina");

  @Bean
  public ChatClient defaultChatClient(ChatClient.Builder chatClientBuilder) {
    return chatClientBuilder
        .defaultUser(DEFAULT_USER)
        .defaultAdvisors(List.of(new SafeGuardAdvisor(SENSITIVE_WORDS), new SimpleLoggerAdvisor(),
            new TokenUsageAuditAdvisor()))
        .build();
  }

  @Bean
  public ChatClient hrChatClient(ChatClient.Builder chatClientBuilder) {
    return chatClientBuilder
        .defaultSystem("""
            You are an internal HR assistant. Your role is to help\s
            employees with questions related to HR policies, such as\s
            leave policies, working hours, benefits, and code of conduct.
            If a user asks for help with anything outside of these topics,\s
            kindly inform them that you can only assist with queries related to\s
            HR policies.
            """
        )
        .defaultAdvisors(new SimpleLoggerAdvisor())
        .defaultUser(DEFAULT_USER)
        .build();
  }

  @Bean
  public ChatClient helpDeskChatClient(ChatClient.Builder chatClientBuilder) {
    return chatClientBuilder
        .defaultSystem("""
            You are an internal IT helpdesk assistant. Your role is to assist 
            employees with IT-related issues such as resetting passwords, 
            unlocking accounts, and answering questions related to IT policies.
            If a user requests help with anything outside of these 
            responsibilities, respond politely and inform them that you are 
            only able to assist with IT support tasks within your defined scope.
            """
        )
        .defaultAdvisors(new TokenUsageAuditAdvisor())
        .defaultUser(DEFAULT_USER)
        .build();
  }

  @Bean
  public ChatClient customerServiceClient(ChatClient.Builder chatClientBuilder) {
    return chatClientBuilder
        .defaultSystem("""
            You are a professional customer service assistant which helps drafting email
            responses to improve the productivity of the customer support team
            """
        )
        .defaultAdvisors(new TokenUsageAuditAdvisor())
        .defaultUser(DEFAULT_USER)
        .build();
  }
}
