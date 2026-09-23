package com.eazybytes.springai.tools;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class TimeTools {

  private static final Logger logger = LoggerFactory.getLogger(TimeTools.class);

  @Tool(name = "getCurrentLocalTime", description = "Get the current time in the user's time zone")
  String getCurrentLocalTime() {
    logger.info("Returning the current time in the user's time zone");
    return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
  }

  @Tool(name = "getCurrentTime", description = "Get the current time in the specified time zone.")
  String getCurrentTime(
      @ToolParam(description = "Value representing the time zone") String timeZone) {
    logger.info("Returning the current time in the time zone {}", timeZone);
    return LocalTime.now(ZoneId.of(timeZone)).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
  }
}
