package com.example.models.transporter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransporterDto {
    @Schema(description = "Name", example = "Red Wings")
    private String name;

    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    @Schema(description = "Phone number", example = "+79192345678")
    private String phoneNumber;
}
