package org.example.models.ticket;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.models.route.RouteEntity;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class TicketResponse {
    private String id;

    private RouteEntity route;

    private Timestamp date;

    private String seatNumber;

    private Integer price;

    private String currency;

    private Boolean isAvailable;
}
