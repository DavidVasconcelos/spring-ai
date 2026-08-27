package com.eazybytes.mcpserverremote.service;

import com.eazybytes.mcpserverremote.entity.HelpDeskTicket;
import com.eazybytes.mcpserverremote.model.TicketRequest;
import java.util.List;

public interface HelpDeskTicketService {

  HelpDeskTicket createTicket(TicketRequest ticketInput);
  List<HelpDeskTicket> getTicketsByUsername(String username);

}
