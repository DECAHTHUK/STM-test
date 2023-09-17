package org.example.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.models.Id;
import org.example.models.route.RouteDto;
import org.example.models.route.RouteEntity;
import org.example.models.route.RouteUpdateRequest;

import java.util.UUID;

@Mapper
public interface RouteMapper {

    @Result(property = "id", column = "id")
    @Select("""
            INSERT INTO routes (departure_point, destination_point, transporter_id, duration_in_minutes)
            VALUES(#{departurePoint}, #{destinationPoint}, #{transporterId}, #{durationInMinutes})
            RETURNING id;
            """)
    Id insertRoute(RouteDto routeDto);

    @Results(value = {
            @Result(property = "departurePoint", column = "departure_point"),
            @Result(property = "destinationPoint", column = "destination_point"),
            @Result(property = "transporter.phoneNumber", column = "phone_number"),
            @Result(property = "durationInMinutes", column = "duration_in_minutes")
    })
    @Select("""
            SELECT r.id, r.departure_point, r.destination_point, r.duration_in_minutes, t.id as trans_id, t.name, t.phone_number
            FROM routes r
            JOIN transporters t on r.transporter_id = t.id
            WHERE r.id = '${uuid};
            """)
    RouteEntity selectRoute(@Param("uuid") UUID uuid);

    @Update("""
            UPDATE routes
            SET departure_point = #{departurePoint}, destination_point = #{destinationPoint}, transporter_id = #{transporterId},
            duration_in_minutes = #{durationInMinutes}
            WHERE id = uuid(#{id})
            """)
    void updateRoute(RouteUpdateRequest routeUpdateRequest);

    @Delete("""
            DELETE FROM route
            WHERE id = '${uuid}';
            """)
    void deleteRoute(@Param("uuid") UUID uuid);
}
