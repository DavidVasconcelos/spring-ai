package com.eazybytes.mcpserverremote.entity;

import com.eazybytes.mcpserverremote.enumerator.TicketPriority;
import com.eazybytes.mcpserverremote.enumerator.TicketStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "helpdesk_tickets")
public class HelpDeskTicket implements Serializable {

  @Serial
  private static final long serialVersionUID = 3029477685758553572L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String issue;

  @Enumerated(EnumType.STRING)
  private TicketStatus status;

  @Enumerated(EnumType.STRING)
  private TicketPriority priority;

  private String contactPhone;

  private LocalDateTime createdAt;

  private LocalDateTime eta;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof HelpDeskTicket that)) {
      return false;
    }
    return Objects.equals(id, that.id) && Objects.equals(username, that.username)
        && Objects.equals(issue, that.issue) && status == that.status
        && Objects.equals(createdAt, that.createdAt) && Objects.equals(eta,
        that.eta);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, issue, status, createdAt, eta);
  }
}

