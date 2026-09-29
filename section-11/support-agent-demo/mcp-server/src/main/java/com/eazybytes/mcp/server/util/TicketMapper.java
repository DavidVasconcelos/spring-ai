package com.eazybytes.mcp.server.util;

import com.eazybytes.mcp.server.domain.entity.SupportTicket;
import com.eazybytes.mcp.server.dto.TicketInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

  TicketInfo toTicketInfo(SupportTicket supportTicket);

}
