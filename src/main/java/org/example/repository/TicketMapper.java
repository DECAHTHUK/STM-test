package org.example.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.example.models.Id;
import org.example.models.ticket.TicketDto;
import org.example.models.ticket.TicketEntity;
import org.example.models.ticket.TicketUpdateRequest;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Mapper
public interface TicketMapper {
    @Results(value = {
            @Result(property = "seatNumber", column = "seat_number"),
            @Result(property = "isAvailable", column = "available"),
            @Result(property = "route.id", column = "route_id"),
            @Result(property = "route.departurePoint", column = "departure_point"),
            @Result(property = "route.destinationPoint", column = "destination_point"),
            @Result(property = "route.transporter.id", column = "trans_id"),
            @Result(property = "route.transporter.name", column = "name"),
            @Result(property = "route.transporter.phoneNumber", column = "phone_number"),
            @Result(property = "route.durationInMinutes", column = "duration_in_minutes")
    })
    @SelectProvider(type = TicketProvider.class, method = "findAvailableTicketsWithFilters")
    List<TicketEntity> findAvailableTicketsWithFilters(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate,
                                                       @Param("departureQuery") String departureQuery, @Param("destinationQuery") String destinationQuery,
                                                       @Param("transporterQuery") String transporterQuery, @Param("offset") Integer offset, @Param("size") Integer size);
    @Result(property = "id", column = "id")
    @Select("""
            INSERT INTO tickets (route_id, date, seat_number, price, currency, available)
            VALUES(#{routeId}, #{date}, #{seatNumber}, #{price}, #{currency}, #{isAvailable})
            RETURNING id;
            """)
    Id insertTicket(TicketDto ticketDto);

    @Results(value = {
            @Result(property = "seatNumber", column = "seat_number"),
            @Result(property = "isAvailable", column = "available"),
            @Result(property = "route.id", column = "route_id"),
            @Result(property = "route.departurePoint", column = "departure_point"),
            @Result(property = "route.destinationPoint", column = "destination_point"),
            @Result(property = "route.transporter.id", column = "trans_id"),
            @Result(property = "route.transporter.phoneNumber", column = "phone_number"),
            @Result(property = "route.durationInMinutes", column = "duration_in_minutes")
    })
    @Select("""
            SELECT t.id, t.date, t.seat_number, t.price, t.currency, t.available, r.id as route_id, r.departure_point,
            r.destination_point, r.duration_in_minutes, transporters.id as trans_id, transporters.name, transporters.phone_number
            FROM tickets t
            JOIN routes r on t.route_id = r.id
            JOIN transporters on r.transporter_id = transporters.id
            WHERE t.id = '${uuid}';
            """)
    TicketEntity selectTicket(@Param("uuid") UUID uuid);

    @Update("""
            UPDATE tickets
            SET route_id = uuid(#{routeId}), date = #{date}, seat_number = #{seatNumber},
            price = #{price}, currency = #{currency}, available = #{isAvailable}
            WHERE id = uuid(#{id})
            """)
    void updateTicket(TicketUpdateRequest ticketUpdateRequest);

    @Delete("""
            DELETE FROM tickets
            WHERE id = '${uuid}';
            """)
    void deleteTicket(@Param("uuid") UUID uuid);

    @Insert("""
            INSERT INTO users_tickets (user_id, ticket_id)
            VALUES ('${userId}', '${ticketId}');
            """)
    void addTicketUserRelation(@Param("ticketId") UUID ticketId, @Param("userId") UUID userId);

    @Results(value = {
            @Result(property = "seatNumber", column = "seat_number"),
            @Result(property = "isAvailable", column = "available"),
            @Result(property = "route.id", column = "route_id"),
            @Result(property = "route.departurePoint", column = "departure_point"),
            @Result(property = "route.destinationPoint", column = "destination_point"),
            @Result(property = "route.transporter.id", column = "trans_id"),
            @Result(property = "route.transporter.phoneNumber", column = "phone_number"),
            @Result(property = "route.durationInMinutes", column = "duration_in_minutes")
    })
    @Select("""
            SELECT t.id, t.date, t.seat_number, t.price, t.currency, t.available, r.id as route_id, r.departure_point,
            r.destination_point, r.duration_in_minutes, transporters.id as trans_id, transporters.name, transporters.phone_number
            FROM users_tickets
            JOIN tickets t on users_tickets.ticket_id = t.id
            JOIN routes r on t.route_id = r.id
            JOIN transporters on r.transporter_id = transporters.id
            WHERE users_tickets.user_id = '${uuid}';
            """)
    List<TicketEntity> selectCurrentUserTickets(@Param("uuid") UUID uuid);
}
