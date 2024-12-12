package com.example.minitor_comm_ms.dtos.builders;

import com.example.minitor_comm_ms.dtos.DeviceDTO;
import com.example.minitor_comm_ms.entities.Device;
import org.modelmapper.ModelMapper;

public class DeviceBuilder {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static DeviceDTO toDeviceDTO(Device device){
        return modelMapper.map(device, DeviceDTO.class);
    }
    public static Device toEntity(DeviceDTO deviceDTO){
        return modelMapper.map(deviceDTO, Device.class);
    }
}