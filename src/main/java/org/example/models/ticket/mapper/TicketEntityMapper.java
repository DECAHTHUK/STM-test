package org.example.models.ticket.mapper;

import org.example.models.ticket.TicketEntity;
import org.example.models.ticket.TicketResponse;
import org.example.models.ticket.TicketUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketEntityMapper {
    TicketResponse ticketToTicketResponse(TicketEntity ticket);

    @Mapping(target = "routeId", source = "route.id")
    TicketUpdateRequest ticketToTicketUpdate(TicketEntity ticket);
}
