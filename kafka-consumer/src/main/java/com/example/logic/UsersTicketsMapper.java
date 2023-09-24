package com.example.logic;

import com.example.models.KafkaTicketPayload;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersTicketsMapper {
    @Insert("""
            INSERT INTO users_tickets (user_id, ticket_id)
            VALUES (uuid(#{userId}), uuid(#{ticketId}));
            """)
    void addTicketUserRelation(KafkaTicketPayload kafkaTicketPayload);
}
