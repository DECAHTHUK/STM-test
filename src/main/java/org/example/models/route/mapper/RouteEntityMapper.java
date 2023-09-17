package org.example.models.route.mapper;

import org.example.models.route.RouteEntity;
import org.example.models.route.RouteResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouteEntityMapper {
    RouteResponse routeToRouteResponse(RouteEntity route);
}
