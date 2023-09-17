package org.example.models.ticket.mapper;

import org.example.models.ticket.TicketEntity;
import org.example.models.ticket.TicketResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketEntityMapper {
    TicketResponse ticketToTicketResponse(TicketEntity ticket);
}
