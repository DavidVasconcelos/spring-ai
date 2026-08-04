package com.eazybytes.springai.controller;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rag")
public class RagController {

  private final ChatClient chatClient;

  public RagController(@Qualifier("chatMemoryChatClient") ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  @GetMapping("/random/chat")
  public ResponseEntity<String> randomChat(@RequestHeader("username") String username,
      @RequestParam("message") String message) {
    String answer = buildAnswer(username, message);

    return ResponseEntity.ok(answer);
  }

  @GetMapping("/document/chat")
  public ResponseEntity<String> documentChat(@RequestHeader("username") String username,
      @RequestParam("message") String message) {
    String answer = buildAnswer(username, message);

    return ResponseEntity.ok(answer);
  }

  private @Nullable String buildAnswer(String username,
      String message) {
    return chatClient.prompt()
        .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
        .user(message)
        .call()
        .content();
  }
}
