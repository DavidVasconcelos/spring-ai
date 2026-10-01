package com.eazybytes.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PromptTemplateController {

  @Value("classpath:/promptTemplates/userPromptTemplate.st")
  Resource userPromptTemplate;

  private final ChatClient customerServiceClient;

  public PromptTemplateController(
      @Qualifier("customerServiceClient") ChatClient customerServiceClient) {
    this.customerServiceClient = customerServiceClient;
  }

  @GetMapping("/customer-service/email")
  public String emailResponse(@RequestParam("customerName") String customerName,
      @RequestParam("customerMessage") String customMessage) {
    return customerServiceClient
        .prompt()
        .user(promptUserSpec -> promptUserSpec
            .text(userPromptTemplate)
            .param("customerName", customerName)
            .param("customerMessage", customMessage)
        )
        .call()
        .content();
  }
}
