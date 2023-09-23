package com.example.kafka;

import com.example.logic.UsersTicketsService;
import com.example.models.KafkaTicketPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class KafkaTicketsListener {
    private final UsersTicketsService usersTicketsService;

    @KafkaListener(topics = "tickets",
        containerFactory = "ticketKafkaListenerContainerFactory")
    void listener(KafkaTicketPayload data) {
        log.info(data.toString());
        usersTicketsService.persistData(data);
    }
}
