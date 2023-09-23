package com.example.models.transporter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@Data
@NoArgsConstructor
public class TransporterEntity {
    @Schema(description = "Id", example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b")
    @UUID(message = "Id should be uuid")
    private String id;

    @Schema(description = "Name", example = "Red Wings")
    private String name;

    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    @Schema(description = "Phone number", example = "+79192345678")
    private String phoneNumber;
}
