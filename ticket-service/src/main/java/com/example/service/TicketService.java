package com.example.service;

import com.example.models.ticket.TicketDto;
import com.example.models.ticket.TicketEntity;
import com.example.models.ticket.TicketUpdateRequest;
import com.example.models.Id;

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

    void buyTicket(UUID uuid);

    List<TicketEntity> getCurrentUserTickets();
}
