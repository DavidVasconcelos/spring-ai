package com.eazybytes.springai.configuration;

import io.qdrant.client.QdrantClient;
import org.springframework.ai.chat.cache.semantic.SemanticCache;
import org.springframework.ai.chat.cache.semantic.SemanticCacheAdvisor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.ai.vectorstore.redis.cache.semantic.DefaultSemanticCache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SemanticCacheConfig {

  @Bean
  public SemanticCacheAdvisor semanticCacheAdvisor(SemanticCache semanticCache) {
    return SemanticCacheAdvisor.builder().cache(semanticCache).build();
  }

  @Bean
  public SemanticCache semanticCache(@Qualifier("cacheVectorStore") VectorStore vectorStore, EmbeddingModel embeddingModel) {
     return DefaultSemanticCache.builder()
         .vectorStore(vectorStore)
         .embeddingModel(embeddingModel)
         .similarityThreshold(0.7)
         .build();
  }

  @Bean("cacheVectorStore")
  VectorStore cacheVectorStore(QdrantClient qdrantClient, EmbeddingModel embeddingModel) {
    return QdrantVectorStore.builder(qdrantClient, embeddingModel)
        .collectionName("eazybytes-semantic-cache")
        .initializeSchema(true)
        .build();
  }
}
