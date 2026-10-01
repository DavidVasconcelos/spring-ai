package com.eazybytes.mcpserverstdio.service.impl;

import com.eazybytes.mcpserverstdio.entity.HelpDeskTicket;
import com.eazybytes.mcpserverstdio.enumerator.TicketStatus;
import com.eazybytes.mcpserverstdio.model.TicketRequest;
import com.eazybytes.mcpserverstdio.repository.HelpDeskTicketRepository;
import com.eazybytes.mcpserverstdio.service.HelpDeskTicketService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelpDeskTicketServiceImpl implements HelpDeskTicketService {

  private final HelpDeskTicketRepository helpDeskTicketRepository;

  @Override
  public HelpDeskTicket createTicket(TicketRequest ticketInput) {
    HelpDeskTicket helpDeskTicket = HelpDeskTicket.builder()
        .issue(ticketInput.issue())
        .username(ticketInput.username())
        .status(TicketStatus.OPEN)
        .createdAt(LocalDateTime.now())
        .eta(LocalDateTime.now().plusDays(7))
        .build();
    return helpDeskTicketRepository.save(helpDeskTicket);
  }

  @Override
  public List<HelpDeskTicket> getTicketsByUsername(String username) {
    return helpDeskTicketRepository.findByUsername(username);
  }
}
