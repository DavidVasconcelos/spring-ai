package com.eazybytes.support.agent.controller;

import com.eazybytes.support.agent.configuration.InboxProperties;
import com.eazybytes.support.agent.dto.SeedResult;
import java.time.Instant;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test-only helper to drop a fake email into the monitored inbox over SMTP, so the inbox monitor
 * has something to pick up.
 *
 * <pre>
 *   curl -X POST "http://localhost:8080/seed-mail?subject=Refund&amp;body=Where%20is%20my%20order%3F"
 * </pre>
 */
@RestController
public class SeedMailController {

  private final JavaMailSender mailSender;
  private final InboxProperties inbox;

  public SeedMailController(JavaMailSender mailSender, InboxProperties inbox) {
    this.mailSender = mailSender;
    this.inbox = inbox;
  }

  @PostMapping("/seed-mail")
  public ResponseEntity<SeedResult> seed(
      @RequestParam(defaultValue = "customer@example.com") String from,
      @RequestParam(defaultValue = "Test support request") String subject,
      @RequestParam(defaultValue = "Hi, I need help with my recent order.") String body) {

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(inbox.address());
    message.setSubject(subject);
    message.setText(body);

    try {
      mailSender.send(message);
    } catch (MailException ex) {
      // Usually means the Mailpit SMTP server (compose.yaml) isn't up yet.
      return ResponseEntity.status(502).body(SeedResult.failed(from, inbox.address(), subject, ex));
    }

    return ResponseEntity.ok(SeedResult.sent(from, inbox.address(), subject, body));
  }
}