package com.eazybytes.springai.configuration;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Profile("!test")
@Configuration
@DependsOn("chatMemoryChatClient")
class ChatMemoryConfig {

  private static final Logger log = LoggerFactory.getLogger(ChatMemoryConfig.class);

  private final List<String> tables =
      Collections.synchronizedList(new ArrayList<>());

  private final JdbcTemplate jdbcTemplate;

  ChatMemoryConfig(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    tables.add("SPRING_AI_CHAT_MEMORY");
    tables.add("HELPDESK_TICKETS");
  }

  @PostConstruct
  void cleanupDatabase() {
    tables.forEach(this::truncateTables);
  }
  
  private void truncateTables(String tableName) {
    try {
      jdbcTemplate.execute("TRUNCATE TABLE " + tableName);
      log.info("Successfully truncated the {} table during application startup.", tableName);
    } catch (RuntimeException ex) {
      log.warn("Could not truncate the {} table: {}", tableName, ex.getMessage());
    }
  }
}
