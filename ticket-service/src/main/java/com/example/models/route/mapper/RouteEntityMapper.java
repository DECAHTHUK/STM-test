package com.example.models.route.mapper;

import com.example.models.route.RouteEntity;
import com.example.models.route.RouteResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouteEntityMapper {
    RouteResponse routeToRouteResponse(RouteEntity route);
}
