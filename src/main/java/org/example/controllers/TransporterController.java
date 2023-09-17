package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.models.Id;
import org.example.models.transporter.TransporterDto;
import org.example.models.transporter.TransporterEntity;
import org.example.models.transporter.TransporterResponse;
import org.example.models.transporter.mapper.TransporterEntityMapper;
import org.example.service.TransporterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/transporters")
public class TransporterController {
    private final TransporterService transporterService;

    private final TransporterEntityMapper transporterEntityMapper;

    @PostMapping
    public Id createNewTransporter(@RequestBody @Valid TransporterDto transporterDto) {
        return transporterService.createTransporter(transporterDto);
    }

    @GetMapping("/{uuid}")
    public TransporterResponse getTransporterById(@PathVariable UUID uuid) {
        return transporterEntityMapper.transporterToTransporterResponse(transporterService.findById(uuid));
    }

    @PutMapping
    public void updateTransporter(@RequestBody @Valid TransporterEntity transporter) {
        transporterService.update(transporter);
    }

    @DeleteMapping("/{uuid}")
    public void deleteTransporter(@PathVariable UUID uuid) {
        transporterService.delete(uuid);
    }
}
