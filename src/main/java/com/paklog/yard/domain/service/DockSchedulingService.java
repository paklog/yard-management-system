package com.paklog.yard.domain.service;

import com.paklog.yard.domain.aggregate.*;
import com.paklog.yard.domain.valueobject.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class DockSchedulingService {
    
    public DockDoor findAvailableDock(List<DockDoor> docks, LocationType preferredType) {
        log.info("Finding available dock of type: {}", preferredType);
        
        return docks.stream()
            .filter(DockDoor::isAvailable)
            .filter(dock -> preferredType == null || dock.getDockType() == preferredType)
            .findFirst()
            .orElse(null);
    }
    
    public void optimizeSchedule(List<Appointment> appointments) {
        log.info("Optimizing schedule for {} appointments", appointments.size());
        // Optimization logic here
    }
}
