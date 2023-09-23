package com.example.service.impl;

import com.example.exceptions.models.NotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.models.Id;
import com.example.models.route.RouteDto;
import com.example.models.route.RouteEntity;
import com.example.models.route.RouteUpdateRequest;
import com.example.repository.RouteMapper;
import com.example.service.RouteService;
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
