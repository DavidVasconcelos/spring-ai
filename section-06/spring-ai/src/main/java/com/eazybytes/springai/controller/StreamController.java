package com.eazybytes.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping
public class StreamController {

  private final ChatClient defaultChatClient;

  public StreamController(@Qualifier("defaultChatClient") ChatClient defaultChatClient) {
    this.defaultChatClient = defaultChatClient;
  }

  @GetMapping("/stream")
  public Flux<String> helpDeskChat(@RequestParam("message") String message) {
    return defaultChatClient.prompt()
        .user(message)
        .stream()
        .content();
  }
}
