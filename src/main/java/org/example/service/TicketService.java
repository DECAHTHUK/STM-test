package org.example.service;

import org.example.models.Id;
import org.example.models.ticket.TicketDto;
import org.example.models.ticket.TicketEntity;
import org.example.models.ticket.TicketUpdateRequest;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface TicketService {
    List<TicketEntity> findAvailable(Timestamp startDate, Timestamp endDate, String departureQuery, String destinationQuery,
                                     String transporterQuery, Integer page, Integer size);
    Id createTicket(TicketDto ticketDto);

    TicketEntity findById(UUID uuid);

    void update(TicketUpdateRequest ticketUpdateRequest);

    void delete(UUID uuid);
}
