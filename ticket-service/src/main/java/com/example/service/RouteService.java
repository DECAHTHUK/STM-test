package com.example.service;

import com.example.models.route.RouteDto;
import com.example.models.route.RouteEntity;
import com.example.models.route.RouteUpdateRequest;
import com.example.models.Id;

import java.util.UUID;

public interface RouteService {
    Id createRoute(RouteDto routeDto);

    RouteEntity findById(UUID uuid);

    void update(RouteUpdateRequest routeUpdateRequest);

    void delete(UUID uuid);
}
