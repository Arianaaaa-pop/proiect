package com.example.minitor_comm_ms.dtos.builders;

import com.example.minitor_comm_ms.dtos.MeasurementDTO;
import com.example.minitor_comm_ms.entities.Measurement;
import org.modelmapper.ModelMapper;

public class MeasurementBuilder {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static MeasurementDTO toMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
    public static Measurement toEntity(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }

}
