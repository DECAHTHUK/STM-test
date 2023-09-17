package org.example.models.transporter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransporterResponse {
    private String id;

    private String name;

    private String phoneNumber;
}
