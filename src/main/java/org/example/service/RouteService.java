package org.example.service;

import org.example.models.Id;
import org.example.models.route.RouteDto;
import org.example.models.route.RouteEntity;
import org.example.models.route.RouteUpdateRequest;

import java.util.UUID;

public interface RouteService {
    Id createRoute(RouteDto routeDto);

    RouteEntity findById(UUID uuid);

    void update(RouteUpdateRequest routeUpdateRequest);

    void delete(UUID uuid);
}
