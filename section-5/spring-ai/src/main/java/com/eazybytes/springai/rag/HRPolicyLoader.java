package com.eazybytes.springai.rag;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class HRPolicyLoader {

  private static final Logger log = LoggerFactory.getLogger(HRPolicyLoader.class);

  @Value("classpath:Eazybytes_HR_Policies.pdf")
  Resource policyFile;

  private final VectorStore vectorStore;

  public HRPolicyLoader(VectorStore vectorStore) {
    this.vectorStore = vectorStore;
  }

  @PostConstruct
  public void loadPDF() {
    List<Document> existing = vectorStore.similaritySearch("EMPLOYMENT POLICIES");

    if (existing.isEmpty()) {
      log.info("Vector Store is empty. Loading initial data...");
      loadContent();
    } else {
      log.info("Data already exists in Vector Store. Skipping load to duplicate the context.");
    }
  }

  private void loadContent() {
    TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(policyFile);
    List<Document> documents = tikaDocumentReader.get();

    TokenTextSplitter textSplitter = TokenTextSplitter.builder()
        .withChunkSize(100) //defaultTokens
        .withMaxNumChunks(400) //maxTokens
        .build();

    vectorStore.add(textSplitter.split(documents));
  }

}
