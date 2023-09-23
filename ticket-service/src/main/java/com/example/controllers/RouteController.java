package com.example.controllers;

import com.example.models.route.RouteDto;
import com.example.models.route.RouteResponse;
import com.example.models.route.RouteUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.models.Id;
import com.example.models.route.mapper.RouteEntityMapper;
import com.example.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Route controller", description = "Route API")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/routes")
public class RouteController {
    private final RouteService routeService;

    private final RouteEntityMapper routeEntityMapper;

    @Operation(summary = "Create new route", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "201",
            description = "Route created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Id.class))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Id createNewRoute(@RequestBody @Valid RouteDto routeDto) {
        return routeService.createRoute(routeDto);
    }

    @Operation(summary = "Get route by id", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "Route found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RouteResponse.class))})
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public RouteResponse getRouteById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Route ID",
                    required = true,
                    schema = @Schema(example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b"))
            @PathVariable UUID uuid) {
        return routeEntityMapper.routeToRouteResponse(routeService.findById(uuid));
    }

    @Operation(summary = "Update route by id", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "Route updated")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateRoute(@RequestBody @Valid RouteUpdateRequest routeUpdateRequest) {
        routeService.update(routeUpdateRequest);
    }

    @Operation(summary = "Delete route by id", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "204",
            description = "Route deleted")
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Route ID",
                    required = true,
                    schema = @Schema(example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b"))
            @PathVariable UUID uuid) {
        routeService.delete(uuid);
    }
}
