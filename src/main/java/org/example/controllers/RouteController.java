package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.models.Id;
import org.example.models.route.RouteDto;
import org.example.models.route.RouteResponse;
import org.example.models.route.RouteUpdateRequest;
import org.example.models.route.mapper.RouteEntityMapper;
import org.example.service.RouteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/routes")
public class RouteController {
    private final RouteService routeService;

    private final RouteEntityMapper routeEntityMapper;

    @PostMapping
    public Id createNewRoute(@RequestBody @Valid RouteDto routeDto) {
        return routeService.createRoute(routeDto);
    }

    @GetMapping("/{uuid}")
    public RouteResponse getRouteById(@PathVariable UUID uuid) {
        return routeEntityMapper.routeToRouteResponse(routeService.findById(uuid));
    }

    @PutMapping
    public void updateRoute(@RequestBody @Valid RouteUpdateRequest routeUpdateRequest) {
        routeService.update(routeUpdateRequest);
    }

    @DeleteMapping("/{uuid}")
    public void deleteRoute(@PathVariable UUID uuid) {
        routeService.delete(uuid);
    }
}
