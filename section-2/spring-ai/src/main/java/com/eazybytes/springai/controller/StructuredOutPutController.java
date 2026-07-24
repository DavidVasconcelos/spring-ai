package com.eazybytes.springai.controller;

import com.eazybytes.springai.model.CountryCities;
import java.util.List;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
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

  @GetMapping("/chat-list")
  public ResponseEntity<List<String>> chatList(@RequestParam("message") String message) {
    List<String> countryCities = defaultChatClient.prompt()
        .user(message)
        .call()
        .entity(new ListOutputConverter());

    return ResponseEntity.ok(countryCities);
  }

  @GetMapping("/chat-map")
  public ResponseEntity<Map<String, Object>> chatMap(@RequestParam("message") String message) {
    Map<String, Object> countryCities = defaultChatClient.prompt()
        .user(message)
        .call()
        .entity(new MapOutputConverter());

    return ResponseEntity.ok(countryCities);
  }

}
