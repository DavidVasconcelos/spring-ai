package com.eazybytes.springai.controller;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import java.util.List;
import java.util.stream.Collectors;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rag")
public class RagController {

  @Value("classpath:/promptTemplates/systemPromptRandomDataTemplate.st")
  Resource randomDataPromptTemplate;

  @Value("classpath:/promptTemplates/systemPromptDocumentTemplate.st")
  Resource documentPromptTemplate;

  private final ChatClient chatClient;
  private final VectorStore vectorStore;

  public RagController(@Qualifier("chatMemoryChatClient") ChatClient chatClient,
      VectorStore vectorStore) {
    this.chatClient = chatClient;
    this.vectorStore = vectorStore;
  }

  @GetMapping("/random/chat")
  public ResponseEntity<String> randomChat(@RequestHeader("username") String username,
      @RequestParam("message") String message) {
    String answer = buildAnswer(randomDataPromptTemplate, username, message);

    return ResponseEntity.ok(answer);
  }


  @GetMapping("/document/chat")
  public ResponseEntity<String> documentChat(@RequestHeader("username") String username,
      @RequestParam("message") String message) {
    String answer = buildAnswer(documentPromptTemplate, username, message);

    return ResponseEntity.ok(answer);
  }

  private @Nullable String buildAnswer(Resource promptTemplate, String username,
      String message) {
    String similarContext = getSimilarContext(message);

    return chatClient.prompt()
        .system(promptSystemSpec -> promptSystemSpec.text(promptTemplate)
            .param("documents", similarContext))
        .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
        .user(message)
        .call()
        .content();
  }

  private @NonNull String getSimilarContext(String message) {
    SearchRequest searchRequest = SearchRequest.builder()
        .query(message)
        .topK(3)
        .similarityThreshold(0.5)
        .build();

    List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);

    return similarDocs.stream()
        .map(Document::getText)
        .collect(Collectors.joining(System.lineSeparator()));
  }
}
