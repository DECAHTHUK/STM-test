package org.example.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Id {
    @Schema(description = "Id", example = "70a359dd-dbc3-4939-aa3d-183baf4d6f0b")
    private String id;
}
