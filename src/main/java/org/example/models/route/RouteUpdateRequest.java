package org.example.models.route;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@Data
@NoArgsConstructor
public class RouteUpdateRequest {
    @Schema(description = "Id", example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b")
    @UUID(message = "Id should be uuid")
    private String id;

    @Schema(description = "Departure point", example = "Cheboksary")
    private String departurePoint;

    @Schema(description = "Destination point", example = "Cheboksary")
    private String destinationPoint;

    @Schema(description = "Id of a transporting company", example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b")
    @UUID(message = "Transporter id should be uuid")
    private String transporterId;

    @Schema(description = "Duration of a flight in minutes", example = "77")
    private Integer durationInMinutes;
}
