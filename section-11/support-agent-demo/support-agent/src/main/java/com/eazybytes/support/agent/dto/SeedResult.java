package com.eazybytes.support.agent.dto;

import java.time.Instant;

/**
 * Outcome of a seeding attempt — echoes back what was injected so the caller can confirm exactly
 * which email the agent is about to pick up.
 *
 * @param status    {@code "sent"} or {@code "failed"}
 * @param message   human-readable description of what happened
 * @param from      the sender address the fake email was sent as
 * @param to        the monitored mailbox it was delivered to
 * @param subject   the email subject
 * @param body      the email body (only present on success)
 * @param error     failure detail (only present on failure)
 * @param timestamp when the attempt was made
 */
public record SeedResult(
    String status,
    String message,
    String from,
    String to,
    String subject,
    String body,
    String error,
    Instant timestamp
) {

  public static SeedResult sent(String from, String to, String subject, String body) {
    return new SeedResult("sent",
        "Email delivered to %s; the agent will pick it up on the next inbox poll.".formatted(to),
        from, to, subject, body, null, Instant.now());
  }

  public static SeedResult failed(String from, String to, String subject, Exception e) {
    return new SeedResult("failed",
        "Could not deliver email to %s. Is the Mailpit SMTP server running?".formatted(to),
        from, to, subject, null, e.getMessage(), Instant.now());
  }
}