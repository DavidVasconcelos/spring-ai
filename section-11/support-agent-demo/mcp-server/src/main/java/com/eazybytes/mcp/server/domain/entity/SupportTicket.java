package com.eazybytes.mcp.server.domain.entity;

import com.eazybytes.mcp.server.domain.enumerator.Channel;
import com.eazybytes.mcp.server.domain.enumerator.Intent;
import com.eazybytes.mcp.server.domain.enumerator.Sentiment;
import com.eazybytes.mcp.server.domain.enumerator.TicketStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "support_tickets")
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_number", nullable = false, updatable = false, unique = true)
    private String ticketNumber = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private Channel channel;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String rawMessage;

    private String detectedLanguage;

    @Enumerated(EnumType.STRING)
    private Intent intent;

    @Enumerated(EnumType.STRING)
    private Sentiment sentiment;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(columnDefinition = "TEXT")
    private String resolution;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime resolvedAt;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SupportTicket that)) {
            return false;
        }
      return Objects.equals(ticketNumber, that.ticketNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ticketNumber);
    }
}