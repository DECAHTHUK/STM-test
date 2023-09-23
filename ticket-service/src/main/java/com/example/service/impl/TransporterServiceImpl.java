package com.example.service.impl;

import com.example.exceptions.models.NotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.models.Id;
import com.example.models.transporter.TransporterDto;
import com.example.models.transporter.TransporterEntity;
import com.example.repository.TransporterMapper;
import com.example.service.TransporterService;
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
