package org.example.models.route;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.models.transporter.TransporterEntity;

@Data
@NoArgsConstructor
public class RouteEntity {
    private String id;

    private String departurePoint;

    private String destinationPoint;

    private TransporterEntity transporter;

    private Integer durationInMinutes;
}
