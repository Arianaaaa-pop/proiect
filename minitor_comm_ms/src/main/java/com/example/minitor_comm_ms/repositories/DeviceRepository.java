package com.example.minitor_comm_ms.repositories;

import com.example.minitor_comm_ms.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {


    List<Device> findByUserId(UUID userId);
}
