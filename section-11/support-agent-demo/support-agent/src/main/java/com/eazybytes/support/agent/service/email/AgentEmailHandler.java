package com.eazybytes.support.agent.service.email;

import com.eazybytes.support.agent.model.AgentResponse;
import com.eazybytes.support.agent.model.IncomingEmail;
import com.eazybytes.support.agent.service.SupportAgent;
import com.eazybytes.support.agent.service.SupportMailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * The real {@link EmailHandler}: hands every new email to the {@link SupportAgent} so the LLM,
 * backed by the MCP tools, resolves it autonomously.
 * <p>
 * Marked {@link Primary} so the {@code InboxMonitor} wires this in ahead of the baseline
 * {@link LoggingEmailHandler}. If the agent throws, we return {@code false} so the message is left
 * unread and retried on the next poll.
 */
@Slf4j
@Primary
@Component
public class AgentEmailHandler implements EmailHandler {

  private final SupportAgent agent;
  private final SupportMailSender mailSender;

  public AgentEmailHandler(SupportAgent agent, SupportMailSender mailSender) {
    this.agent = agent;
    this.mailSender = mailSender;
  }

  @Override
  public boolean handle(IncomingEmail email) {
    log.info("Handing email from {} (subject: \"{}\") to the support agent", email.from(),
        email.subject());
    try {
      AgentResponse response = agent.resolve(email);
      log.info("""
              === Agent resolution ===
              From    : {}
              Subject : {}
              Outcome :
              {}
              ========================""",
          email.from(), email.subject(), response.operatorSummary());
      // Send the drafted reply back to the customer, threaded onto their email.
      return mailSender.sendReply(email, response);
    } catch (Exception e) {
      // Anything from a transient LLM/MCP error to a tool failure lands here.
      // Leave the mail unread so the next poll retries it.
      log.error("Agent failed to resolve email from {} (subject: \"{}\"); will retry",
          email.from(), email.subject(), e);
      return false;
    }
  }
}