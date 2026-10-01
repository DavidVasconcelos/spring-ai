package com.eazybytes.springai.controller;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tools")
public class TimeController {

  private final ChatClient chatClient;

  public TimeController(@Qualifier("timeChatClient") ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  @GetMapping("/local-time")
  public ResponseEntity<String> localTime(@RequestHeader("username") String username,
      @RequestParam String message) {
    String answer = chatClient.prompt()
        .advisors(advisor -> advisor.param(CONVERSATION_ID, username))
        .user(message)
        .call()
        .content();
    return ResponseEntity.ok(answer);
  }

}
