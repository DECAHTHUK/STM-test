package com.example.models.route;

import com.example.models.transporter.TransporterEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RouteEntity {
    private String id;

    private String departurePoint;

    private String destinationPoint;

    private TransporterEntity transporter;

    private Integer durationInMinutes;
}
