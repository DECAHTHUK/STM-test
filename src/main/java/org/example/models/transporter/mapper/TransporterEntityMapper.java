package org.example.models.transporter.mapper;

import org.example.models.transporter.TransporterEntity;
import org.example.models.transporter.TransporterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransporterEntityMapper {
    TransporterResponse transporterToTransporterResponse(TransporterEntity transporter);
}
