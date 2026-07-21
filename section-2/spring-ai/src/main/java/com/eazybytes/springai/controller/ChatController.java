package com.eazybytes.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ChatController {

  private final ChatClient hrChatClient;
  private final ChatClient helpDeskChatClient;
  private final ChatClient defaultChatClient;

  public ChatController(@Qualifier("hrChatClient") ChatClient hrChatClient,
      @Qualifier("helpDeskChatClient") ChatClient helpDeskChatClient,
      @Qualifier("defaultChatClient") ChatClient defaultChatClient) {
    this.hrChatClient = hrChatClient;
    this.helpDeskChatClient = helpDeskChatClient;
    this.defaultChatClient = defaultChatClient;
  }

  @GetMapping("/hr/chat")
  public String hrChat(@RequestParam("message") String message) {
    return hrChatClient.prompt()
        .user(message)
        .call()
        .content();
  }

  @GetMapping("/general/chat")
  public String helpDeskChat(@RequestParam("message") String message) {
    return defaultChatClient.prompt()
        .user(message)
        .call()
        .content();
  }
}
