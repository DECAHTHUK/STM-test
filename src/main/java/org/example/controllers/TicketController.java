package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.models.Id;
import org.example.models.ticket.TicketDto;
import org.example.models.ticket.TicketResponse;
import org.example.models.ticket.TicketUpdateRequest;
import org.example.models.ticket.mapper.TicketEntityMapper;
import org.example.service.TicketService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    private final TicketEntityMapper ticketEntityMapper;
    @PostMapping
    public Id createNewTicket(@RequestBody @Valid TicketDto ticketDto) {
        return ticketService.createTicket(ticketDto);
    }

    @GetMapping("/{uuid}")
    public TicketResponse getTicketById(@PathVariable UUID uuid) {
        return ticketEntityMapper.ticketToTicketResponse(ticketService.findById(uuid));
    }

    @PutMapping
    public void updateTicket(@RequestBody @Valid TicketUpdateRequest ticketUpdateRequest) {
        ticketService.update(ticketUpdateRequest);
    }

    @DeleteMapping("/{uuid}")
    public void deleteTicket(@PathVariable UUID uuid) {
        ticketService.delete(uuid);
    }
}
