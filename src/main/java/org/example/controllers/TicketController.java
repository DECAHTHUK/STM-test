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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    private final TicketEntityMapper ticketEntityMapper;

    //Well filtering could have been made much more abstract, but i think for now it fits the technical task
    @GetMapping("/available")
    public List<TicketResponse> getAvailableTickets(@RequestParam(name = "start_date", required = false) Timestamp startDate,
                                                    @RequestParam(name = "end_date", required = false) Timestamp endDate,
                                                    @RequestParam(name = "departure_query", required = false) String departureQuery,
                                                    @RequestParam(name = "destination_query", required = false) String destinationQuery,
                                                    @RequestParam(name = "transporter_query", required = false) String transporterQuery,
                                                    @RequestParam(name = "page") Integer page,
                                                    @RequestParam(name = "size", required = false) Integer size) {
        return ticketService.findAvailable(startDate, endDate, departureQuery, destinationQuery, transporterQuery, page, size)
                .stream().map(ticketEntityMapper::ticketToTicketResponse).toList();
    }

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
