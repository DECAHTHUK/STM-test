package com.example.controllers;

import com.example.models.transporter.TransporterDto;
import com.example.models.transporter.TransporterEntity;
import com.example.models.transporter.TransporterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.models.Id;
import com.example.models.transporter.mapper.TransporterEntityMapper;
import com.example.service.TransporterService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Transporter controller", description = "Transporter API")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/transporters")
public class TransporterController {
    private final TransporterService transporterService;

    private final TransporterEntityMapper transporterEntityMapper;

    @Operation(summary = "Create new transporter", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "201",
            description = "Transporter created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Id.class))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Id createNewTransporter(@RequestBody @Valid TransporterDto transporterDto) {
        return transporterService.createTransporter(transporterDto);
    }

    @Operation(summary = "Get transporter by id", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "Transporter found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TransporterResponse.class))})
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public TransporterResponse getTransporterById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Transporter ID",
                    required = true,
                    schema = @Schema(example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b"))
            @PathVariable UUID uuid) {
        return transporterEntityMapper.transporterToTransporterResponse(transporterService.findById(uuid));
    }

    @Operation(summary = "Update transporter by id", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "200",
            description = "Transporter updated")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateTransporter(@RequestBody @Valid TransporterEntity transporter) {
        transporterService.update(transporter);
    }

    @Operation(summary = "Delete transporter by id", description = "Author: Talanov Alexey")
    @ApiResponse(
            responseCode = "204",
            description = "Transporter deleted")
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransporter(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Transporter ID",
                    required = true,
                    schema = @Schema(example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b"))
            @PathVariable UUID uuid) {
        transporterService.delete(uuid);
    }
}
