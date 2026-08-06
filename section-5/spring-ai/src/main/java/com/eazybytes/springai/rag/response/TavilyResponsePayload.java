package com.eazybytes.springai.rag.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TavilyResponsePayload(String query,
                                    List<String> followUpQuestions,
                                    String answer,
                                    List<String> images,
                                    List<Hit> results,
                                    Double responseTime,
                                    String requestId) {

  public record Hit(
      String url,
      String title,
      String content,
      Double score,
      String rawContent,
      String id
  ) {

  }
}
