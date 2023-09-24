package com.example.controllers;

import com.example.models.ticket.TicketDto;
import com.example.models.ticket.TicketResponse;
import com.example.models.ticket.TicketUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.models.Id;
import com.example.models.ticket.mapper.TicketEntityMapper;
import com.example.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Tag(name = "Ticket controller", description = "Ticket API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    private final TicketEntityMapper ticketEntityMapper;

    //Well filtering could have been made much more abstract, but i think for now it fits the technical task
    @Operation(summary = "Get all available tickets", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "Found tickets",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TicketResponse.class)))})
    @GetMapping("/available")
    @ResponseStatus(HttpStatus.OK)
    public List<TicketResponse> getAvailableTickets(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Filter to filter out tickets with date after the start_date",
                    schema = @Schema(example = "2023-09-20 17:01:00"))
            @RequestParam(name = "start_date", required = false) Timestamp startDate,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Filter to filter out tickets with date before the end_date",
                    schema = @Schema(example = "2023-09-20 17:01:00"))
            @RequestParam(name = "end_date", required = false) Timestamp endDate,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Filter to filter out tickets with departure point starting with departure_query",
                    schema = @Schema(example = "Bar"))
            @RequestParam(name = "departure_query", required = false) String departureQuery,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Filter to filter out tickets with destination point starting with destination_query",
                    schema = @Schema(example = "Ni"))
            @RequestParam(name = "destination_query", required = false) String destinationQuery,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Filter to filter out tickets with transporter name starting with transporter_query",
                    schema = @Schema(example = "Red"))
            @RequestParam(name = "transporter_query", required = false) String transporterQuery,
            @Parameter(
                    in = ParameterIn.QUERY, required = true,
                    description = "Results page you want to retrieve (0..N)",
                    schema = @Schema(type = "integer", minimum = "0"))
            @RequestParam(name = "page") Integer page,
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Number of records per page(by default 5)",
                    schema = @Schema(type = "integer", minimum = "0"))
            @RequestParam(name = "size", required = false) Integer size) {
        return ticketService.findAvailable(startDate, endDate, departureQuery, destinationQuery, transporterQuery, page, size)
                .stream().map(ticketEntityMapper::ticketToTicketResponse).toList();
    }

    @Operation(summary = "Buy ticket by id", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "Ticket bought")
    @PatchMapping("/{uuid}/buy")
    @ResponseStatus(HttpStatus.OK)
    public void buyTicket(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Ticket ID",
                    required = true,
                    schema = @Schema(example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b"))
            @PathVariable UUID uuid) {
        ticketService.buyTicket(uuid);
    }

    @Operation(summary = "Get all tickets bought by current user", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "Owned tickets",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TicketResponse.class)))})
    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public List<TicketResponse> getAllBoughtTickets() {
        return ticketService.getCurrentUserTickets().stream().map(ticketEntityMapper::ticketToTicketResponse).toList();
    }

    @Operation(summary = "Create new ticket", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "201",
            description = "Ticket created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Id.class))})
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Id createNewTicket(@RequestBody @Valid TicketDto ticketDto) {
        return ticketService.createTicket(ticketDto);
    }

    @Operation(summary = "Get ticket by id", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "Ticket found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponse.class))})
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public TicketResponse getTicketById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Ticket ID",
                    required = true,
                    schema = @Schema(example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b"))
            @PathVariable UUID uuid) {
        return ticketEntityMapper.ticketToTicketResponse(ticketService.findById(uuid));
    }

    @Operation(summary = "Update ticket by id", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "Ticket updated")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateTicket(@RequestBody @Valid TicketUpdateRequest ticketUpdateRequest) {
        ticketService.update(ticketUpdateRequest);
    }

    @Operation(summary = "Delete ticket by id", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "204",
            description = "Ticket deleted")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Ticket ID",
                    required = true,
                    schema = @Schema(example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b"))
            @PathVariable UUID uuid) {
        ticketService.delete(uuid);
    }
}
