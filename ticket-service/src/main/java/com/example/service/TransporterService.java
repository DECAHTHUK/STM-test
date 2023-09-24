package com.example.service;

import com.example.models.transporter.TransporterDto;
import com.example.models.transporter.TransporterEntity;
import com.example.models.Id;

import java.util.UUID;

public interface TransporterService {
    Id createTransporter(TransporterDto transporterDto);

    TransporterEntity findById(UUID uuid);

    void update(TransporterEntity transporter);

    void delete(UUID uuid);
}
