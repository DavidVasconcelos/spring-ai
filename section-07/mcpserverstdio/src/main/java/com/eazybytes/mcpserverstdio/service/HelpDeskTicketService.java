package com.eazybytes.mcpserverstdio.service;

import com.eazybytes.mcpserverstdio.entity.HelpDeskTicket;
import com.eazybytes.mcpserverstdio.model.TicketRequest;
import java.util.List;

public interface HelpDeskTicketService {

  HelpDeskTicket createTicket(TicketRequest ticketInput);
  List<HelpDeskTicket> getTicketsByUsername(String username);

}
