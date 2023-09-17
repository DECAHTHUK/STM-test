package org.example.service;

import org.example.models.Id;
import org.example.models.transporter.TransporterDto;
import org.example.models.transporter.TransporterEntity;

import java.util.UUID;

public interface TransporterService {
    Id createTransporter(TransporterDto transporterDto);

    TransporterEntity findById(UUID uuid);

    void update(TransporterEntity transporter);

    void delete(UUID uuid);
}
