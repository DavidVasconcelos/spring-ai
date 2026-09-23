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
public class PromptStuffingController {

  @Value("classpath:/promptTemplates/systemPromptTemplate.st")
  Resource systemPromptTemplate;

  private final ChatClient defaultChatClient;

  public PromptStuffingController(@Qualifier("defaultChatClient") ChatClient defaultChatClient) {
    this.defaultChatClient = defaultChatClient;
  }

  @GetMapping("/hr/prompt-stuffing")
  public String emailResponse(@RequestParam("message") String message) {
    return defaultChatClient
        .prompt()
        .system(systemPromptTemplate)
        .user(message)
        .call()
        .content();
  }
}
