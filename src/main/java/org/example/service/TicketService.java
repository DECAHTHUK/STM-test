package org.example.service;

import org.example.models.Id;
import org.example.models.ticket.TicketDto;
import org.example.models.ticket.TicketEntity;
import org.example.models.ticket.TicketUpdateRequest;

import java.util.UUID;

public interface TicketService {
    Id createTicket(TicketDto ticketDto);

    TicketEntity findById(UUID uuid);

    void update(TicketUpdateRequest ticketUpdateRequest);

    void delete(UUID uuid);
}
