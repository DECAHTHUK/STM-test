package com.example.models.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class TicketDto {
    @UUID(message = "Route id should be uuid")
    @Schema(description = "Route id", example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b")
    private String routeId;

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
