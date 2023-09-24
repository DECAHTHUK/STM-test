package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Better to define this class in common library
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaTicketPayload {
    private String ticketId;

    private String userId;
}
