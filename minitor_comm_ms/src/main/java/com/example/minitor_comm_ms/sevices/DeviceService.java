package com.example.minitor_comm_ms.sevices;

import com.example.minitor_comm_ms.dtos.DeviceDTO;
import com.example.minitor_comm_ms.dtos.builders.DeviceBuilder;
import com.example.minitor_comm_ms.entities.Device;
import com.example.minitor_comm_ms.repositories.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DeviceService.class);

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public UUID insert(DeviceDTO deviceDTO ){

        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        LOGGER.info("Device with id {} was inserted in db", device.getId());
        return device.getId();
    }

    public List<DeviceDTO> findDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }


}
