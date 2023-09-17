package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.models.NotFoundException;
import org.example.models.Id;
import org.example.models.route.RouteDto;
import org.example.models.route.RouteEntity;
import org.example.models.route.RouteUpdateRequest;
import org.example.repository.RouteMapper;
import org.example.service.RouteService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteMapper routeMapper;

    @Override
    public Id createRoute(RouteDto routeDto) {
        return routeMapper.insertRoute(routeDto);
    }

    @Override
    public RouteEntity findById(UUID uuid) {
        RouteEntity route = routeMapper.selectRoute(uuid);
        if (route == null) {
            throw new NotFoundException("Route with id = " + uuid + " was not found");
        }
        return route;
    }

    @Override
    public void update(RouteUpdateRequest routeUpdateRequest) {
        routeMapper.updateRoute(routeUpdateRequest);
    }

    @Override
    public void delete(UUID uuid) {
        routeMapper.deleteRoute(uuid);
    }
}
