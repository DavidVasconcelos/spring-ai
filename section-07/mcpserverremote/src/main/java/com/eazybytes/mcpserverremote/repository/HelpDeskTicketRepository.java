package com.eazybytes.mcpserverremote.repository;

import com.eazybytes.mcpserverremote.entity.HelpDeskTicket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpDeskTicketRepository extends JpaRepository<HelpDeskTicket, Long> {

  List<HelpDeskTicket> findByUsername(String username);
}
