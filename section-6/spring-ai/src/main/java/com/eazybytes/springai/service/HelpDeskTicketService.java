package com.eazybytes.springai.service;

import com.eazybytes.springai.entity.HelpDeskTicket;
import com.eazybytes.springai.model.TicketRequest;
import java.util.List;

public interface HelpDeskTicketService {

  HelpDeskTicket createTicket(TicketRequest ticketInput, String username);
  List<HelpDeskTicket> getTicketsByUsername(String username);

}
