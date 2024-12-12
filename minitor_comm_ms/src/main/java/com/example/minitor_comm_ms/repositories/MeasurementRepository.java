package com.example.minitor_comm_ms.repositories;

import com.example.minitor_comm_ms.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    // Find measurements by deviceId
    List<Measurement> findByDeviceId(UUID id);

    // Find measurements by timestamp range
  //  List<Measurement> findByTimestampBetween(String startTime, String endTime);
}
