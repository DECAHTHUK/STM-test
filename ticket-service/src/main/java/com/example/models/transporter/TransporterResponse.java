package com.example.models.transporter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransporterResponse {
    @Schema(description = "Id", example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b")
    private String id;

    @Schema(description = "Name", example = "Red Wings")
    private String name;

    @Schema(description = "Phone number", example = "+79192345678")
    private String phoneNumber;
}
