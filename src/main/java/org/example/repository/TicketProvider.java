package org.example.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Timestamp;

public class TicketProvider {
    @SuppressWarnings("unused")
    public static String findAvailableTicketsWithFilters(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate,
                             @Param("departureQuery") String departureQuery, @Param("destinationQuery") String destinationQuery,
                             @Param("transporterQuery") String transporterQuery, @Param("offset") Integer offset, @Param("size") Integer size) {
        return new SQL() {{
            SELECT("""
                    t.id, t.date, t.seat_number, t.price, t.currency, t.available, r.id as route_id, r.departure_point,
                    r.destination_point, r.duration_in_minutes, transporters.id as trans_id, transporters.name, transporters.phone_number
                    """);
            FROM("tickets t");
            JOIN("routes r on t.route_id = r.id", "transporters on r.transporter_id = transporters.id");
            WHERE("t.available = true");
            if (startDate != null) {
                AND().WHERE("t.date >= #{startDate}");
            }
            if (endDate != null) {
                AND().WHERE("t.date <= #{endDate}");
            }
            if (departureQuery != null) {
                AND().WHERE("r.departure_point ILIKE CONCAT(#{departureQuery}, '%')");
            }
            if (destinationQuery != null) {
                AND().WHERE("r.destination_point ILIKE CONCAT(#{destinationQuery}, '%')");
            }
            if (transporterQuery != null) {
                AND().WHERE("transporters.name ILIKE CONCAT(#{transporterQuery}, '%')");
            }
            ORDER_BY("t.date");
            OFFSET(offset).LIMIT(size);
        }}.toString();
    }
}
