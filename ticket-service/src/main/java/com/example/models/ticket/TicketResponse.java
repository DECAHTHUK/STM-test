package com.example.models.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.models.route.RouteEntity;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class TicketResponse {
    @Schema(description = "Id", example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b")
    private String id;

    @Schema(description = "Route")
    private RouteEntity route;

    @Schema(description = "Route", example = "2023-09-20 17:01:00")
    private Timestamp date;

    @Schema(description = "Seat number", example = "22F")
    private String seatNumber;

    @Schema(description = "Price", example = "200")
    private Integer price;

    @Schema(description = "Currency", example = "USD")
    private String currency;

    @Schema(description = "Is ticket already bought", example = "false")
    private Boolean isAvailable;
}
