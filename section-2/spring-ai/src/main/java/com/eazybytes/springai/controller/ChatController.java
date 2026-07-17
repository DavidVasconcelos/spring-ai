package com.eazybytes.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

  private final ChatClient hrChatClient;
  private final ChatClient helpDeskChatClient;

  public ChatController(@Qualifier("hrChatClient") ChatClient hrChatClient,
      @Qualifier("helpDeskChatClient") ChatClient helpDeskChatClient) {
    this.hrChatClient = hrChatClient;
    this.helpDeskChatClient = helpDeskChatClient;
  }

  @GetMapping("/hr/chat")
  public String hrChat(@RequestParam("message") String message) {
    return hrChatClient.prompt()
        .user(message)
        .call()
        .content();
  }

  @GetMapping("/help-desk/chat")
  public String helpDeskChat(@RequestParam("message") String message) {
    return helpDeskChatClient.prompt()
        .user(message)
        .call()
        .content();
  }
}
