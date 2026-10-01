package com.eazybytes.springai.configuration;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@DependsOn("chatMemoryChatClient")
public class ChatMemoryConfig {

  private static final Logger log = LoggerFactory.getLogger(ChatMemoryConfig.class);

  private static final String TABLE_NAME = "SPRING_AI_CHAT_MEMORY";
  private final JdbcTemplate jdbcTemplate;

  public ChatMemoryConfig(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @PostConstruct
  public void cleanupDatabase() {
    try {
      jdbcTemplate.execute("TRUNCATE TABLE " + TABLE_NAME);
      log.info("Successfully truncated the {} table during application startup.", TABLE_NAME);
    } catch (RuntimeException ex) {
      log.warn("Could not truncate the {} table: {}", TABLE_NAME, ex.getMessage());
    }
  }
}
