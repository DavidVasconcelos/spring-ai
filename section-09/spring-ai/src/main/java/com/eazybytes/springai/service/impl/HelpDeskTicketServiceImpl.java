package com.eazybytes.springai.service.impl;

import com.eazybytes.springai.entity.HelpDeskTicket;
import com.eazybytes.springai.enumerator.TicketStatus;
import com.eazybytes.springai.model.TicketRequest;
import com.eazybytes.springai.repository.HelpDeskTicketRepository;
import com.eazybytes.springai.service.HelpDeskTicketService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelpDeskTicketServiceImpl implements HelpDeskTicketService {

  private final HelpDeskTicketRepository helpDeskTicketRepository;

  @Override
  public HelpDeskTicket createTicket(TicketRequest ticketInput, String username) {
    HelpDeskTicket helpDeskTicket = HelpDeskTicket.builder()
        .issue(ticketInput.issue())
        .username(username)
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
