package org.example.models.ticket;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class TicketUpdateRequest {
    private String id;

    private String routeId;

    private Timestamp date;

    private Integer seatNumber;

    private Integer price;

    private String currency;

    private Boolean isAvailable;
}