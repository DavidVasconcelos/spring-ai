package com.eazybytes.support.agent.service.email;

import com.eazybytes.support.agent.model.IncomingEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Default {@link EmailHandler} that simply logs whatever lands in the inbox.
 * <p>
 * It is the baseline so the monitoring loop is verifiable on its own. Replace or wrap it once the
 * agent's real actions (classification, LLM reply drafting, tool calls, ...) are wired in.
 */
@Slf4j
@Component
public class LoggingEmailHandler implements EmailHandler {

  @Override
  public boolean handle(IncomingEmail email) {
    log.info("""
            === New email ===
            From    : {}
            To      : {}
            Subject : {}
            Body    :
            {}
            =================""",
        email.from(), email.to(), email.subject(), email.body());
    return true;
  }
}