package com.example.models.transporter.mapper;

import com.example.models.transporter.TransporterEntity;
import com.example.models.transporter.TransporterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransporterEntityMapper {
    TransporterResponse transporterToTransporterResponse(TransporterEntity transporter);
}
