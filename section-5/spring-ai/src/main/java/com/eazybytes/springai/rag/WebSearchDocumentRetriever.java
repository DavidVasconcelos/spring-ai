package com.eazybytes.springai.rag;

import com.eazybytes.springai.rag.request.TavilyRequestPayload;
import com.eazybytes.springai.rag.response.TavilyResponsePayload;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;

public class WebSearchDocumentRetriever implements DocumentRetriever {

  private static final Logger logger = LoggerFactory.getLogger(WebSearchDocumentRetriever.class);

  private static final String TAVILY_API_KEY = "TAVILY_SEARCH_API_KEY";
  private static final String BASE_URL = "https://api.tavily.com/search";
  private static final int DEFAULT_RESULT_LIMIT = 5;

  private final RestClient restClient;
  private final int resultLimit;

  public WebSearchDocumentRetriever(RestClient.Builder clientBuilder, int resultLimit) {
    Assert.notNull(clientBuilder, "clientBuilder must not be null");

    String apiKey = System.getenv(TAVILY_API_KEY);
    Assert.hasText(apiKey, "Environment variable TAVILY_SEARCH_API_KEY must be set");

    this.restClient = clientBuilder
        .baseUrl(BASE_URL)
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
        .build();

    if (resultLimit <= 0) {
      throw new IllegalArgumentException("Result limit must be greater than 0");
    }

    this.resultLimit = resultLimit;
  }

  @Override
  public @NonNull List<Document> retrieve(Query query) {
    Assert.notNull(query, "query must not be null");

    String queryText = query.text();
    Assert.hasText(queryText, "query text must not be empty");
    logger.info("Processing query {}", queryText);

    TavilyResponsePayload responsePayload = restClient.post()
        .body(new TavilyRequestPayload(queryText, "advanced", resultLimit))
        .retrieve()
        .body(TavilyResponsePayload.class);

    if(responsePayload == null || CollectionUtils.isEmpty(responsePayload.results())) {
      return List.of();
    }

    return responsePayload.results().stream()
        .map(hit -> Document.builder()
                .text(hit.content())
                .metadata("title", hit.title())
                .metadata("url", hit.url())
                .score(hit.score())
                .build()
            )
        .toList();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private RestClient.Builder clientBuilder;
    private int resultLimit = DEFAULT_RESULT_LIMIT;

    private Builder() {}

    public Builder restClientBuilder(RestClient.Builder clientBuilder) {
      this.clientBuilder = clientBuilder;
      return this;
    }

    public Builder maxResults(int maxResults) {
      if (maxResults <= 0) {
        throw new IllegalArgumentException("maxResults must be greater than 0");
      }
      this.resultLimit = maxResults;
      return this;
    }

    public WebSearchDocumentRetriever build() {
      return new WebSearchDocumentRetriever(clientBuilder, resultLimit);
    }
  }
}
