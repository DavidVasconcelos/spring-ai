package com.eazybytes.mcpserverstdio.repository;

import com.eazybytes.mcpserverstdio.entity.HelpDeskTicket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpDeskTicketRepository extends JpaRepository<HelpDeskTicket, Long> {

  List<HelpDeskTicket> findByUsername(String username);
}
