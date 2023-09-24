package com.example.logic;

import com.example.models.KafkaTicketPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersTicketsService {
    private final UsersTicketsMapper usersTicketsMapper;

    public void persistData(KafkaTicketPayload kafkaTicketPayload) {
        usersTicketsMapper.addTicketUserRelation(kafkaTicketPayload);
    }
}
