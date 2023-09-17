package org.example.models.route;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RouteUpdateRequest {
    private String id;

    private String departurePoint;

    private String destinationPoint;

    private String transporterId;

    private Integer durationInMinutes;
}
