package com.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;

// Better to define this class in common library
@Data
@NoArgsConstructor
public class KafkaTicketPayload {
    private String ticketId;

    private String userId;
}
