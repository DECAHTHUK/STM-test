package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.models.NotFoundException;
import org.example.models.Id;
import org.example.models.transporter.TransporterDto;
import org.example.models.transporter.TransporterEntity;
import org.example.repository.TransporterMapper;
import org.example.service.TransporterService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransporterServiceImpl implements TransporterService {
    private final TransporterMapper transporterMapper;

    @Override
    public Id createTransporter(TransporterDto transporterDto) {
        return transporterMapper.insertTransporter(transporterDto);
    }

    @Override
    public TransporterEntity findById(UUID uuid) {
        TransporterEntity transporter = transporterMapper.selectTransporter(uuid);
        if (transporter == null) {
            throw new NotFoundException("Transporter with id = " + uuid + " was not found");
        }
        return transporter;
    }

    @Override
    public void update(TransporterEntity transporter) {
        transporterMapper.updateTransporter(transporter);
    }

    @Override
    public void delete(UUID uuid) {
        transporterMapper.deleteTransporter(uuid);
    }
}
