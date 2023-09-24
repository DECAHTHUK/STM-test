package com.example.service.impl;

import com.example.exceptions.models.NotFoundException;
import com.example.exceptions.models.TicketAlreadyBookedException;
import lombok.RequiredArgsConstructor;
import com.example.models.Id;
import com.example.models.KafkaTicketPayload;
import com.example.models.ticket.TicketDto;
import com.example.models.ticket.TicketEntity;
import com.example.models.ticket.TicketUpdateRequest;
import com.example.models.ticket.mapper.TicketEntityMapper;
import com.example.repository.TicketMapper;
import com.example.security.models.JwtAuthentication;
import com.example.service.TicketService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yaml")
public class TicketServiceImpl implements TicketService {
    private final TicketMapper ticketMapper;

    private final TicketEntityMapper ticketEntityMapper;

    private final KafkaTemplate<String, KafkaTicketPayload> kafkaTemplate;

    @Value(value = "${spring.kafka.topic}")
    private String topic;

    @Value("${pagination}")
    private int ROWS_AMOUNT;

    @Override
    public List<TicketEntity> findAvailable(Timestamp startDate, Timestamp endDate, String departureQuery, String destinationQuery,
                                            String transporterQuery, Integer page, Integer size) {
        int paginationSize = size == null ? ROWS_AMOUNT : size;
        return ticketMapper.findAvailableTicketsWithFilters(startDate, endDate, departureQuery, destinationQuery, transporterQuery,
                page * paginationSize, paginationSize);
    }

    @Override
    @Transactional
    public void buyTicket(UUID uuid) {
        TicketEntity ticket = findById(uuid);
        if (!ticket.getIsAvailable()) {
            throw new TicketAlreadyBookedException("Ticket with id " + uuid + " already booked");
        }
        ticket.setIsAvailable(false);
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        update(ticketEntityMapper.ticketToTicketUpdate(ticket));
        ticketMapper.addTicketUserRelation(uuid, UUID.fromString(authentication.getUsername()));
        kafkaTemplate.send(topic, new KafkaTicketPayload(uuid.toString(), authentication.getUsername()));
    }

    @Override
    public List<TicketEntity> getCurrentUserTickets() {
        JwtAuthentication authentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return ticketMapper.selectCurrentUserTickets(UUID.fromString(authentication.getPrincipal().toString()));
    }

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
