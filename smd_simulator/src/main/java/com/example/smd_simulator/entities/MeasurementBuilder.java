package com.example.smd_simulator.entities;

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
