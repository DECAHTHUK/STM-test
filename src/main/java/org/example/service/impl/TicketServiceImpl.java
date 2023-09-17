package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.models.NotFoundException;
import org.example.models.Id;
import org.example.models.ticket.TicketDto;
import org.example.models.ticket.TicketEntity;
import org.example.models.ticket.TicketUpdateRequest;
import org.example.repository.TicketMapper;
import org.example.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketMapper ticketMapper;

    @Override
    public Id createTicket(TicketDto ticketDto) {
        return ticketMapper.insertTicket(ticketDto);
    }

    @Override
    public TicketEntity findById(UUID uuid) {
        TicketEntity ticket = ticketMapper.selectTicket(uuid);
        if (ticket == null) {
            throw new NotFoundException("Ticket with id = " + uuid + " was not found");
        }
        return ticket;
    }

    @Override
    public void update(TicketUpdateRequest ticketUpdateRequest) {
        ticketMapper.updateTicket(ticketUpdateRequest);
    }

    @Override
    public void delete(UUID uuid) {
        ticketMapper.deleteTicket(uuid);
    }
}
