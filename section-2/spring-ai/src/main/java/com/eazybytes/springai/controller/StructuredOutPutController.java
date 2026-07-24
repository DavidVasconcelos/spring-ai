package com.eazybytes.springai.controller;

import com.eazybytes.springai.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class StructuredOutPutController {

  private final ChatClient defaultChatClient;

  public StructuredOutPutController(@Qualifier("defaultChatClient") ChatClient defaultChatClient) {
    this.defaultChatClient = defaultChatClient;
  }

  @GetMapping("/chat-bean")
  public ResponseEntity<CountryCities> chatBean(@RequestParam("message") String message) {
    CountryCities countryCities = defaultChatClient.prompt()
        .user(message)
        .call()
        .entity(CountryCities.class);

    return ResponseEntity.ok(countryCities);
  }

}
