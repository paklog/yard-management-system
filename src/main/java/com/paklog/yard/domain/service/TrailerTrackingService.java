package com.paklog.yard.domain.service;

import com.paklog.yard.domain.aggregate.Trailer;
import com.paklog.yard.domain.valueobject.GPSCoordinates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrailerTrackingService {
    
    public void updateTrailerLocation(Trailer trailer, GPSCoordinates newLocation) {
        log.info("Updating location for trailer: {}", trailer.getTrailerNumber());
        
        GPSCoordinates oldLocation = trailer.getCurrentLocation();
        if (oldLocation != null) {
            double distance = oldLocation.distanceTo(newLocation);
            log.debug("Trailer moved {} meters", distance);
        }
        
        trailer.updateLocation(newLocation);
    }
    
    public double calculateDwellTime(Trailer trailer) {
        if (trailer.getArrivedAt() == null) return 0.0;
        
        java.time.Instant now = java.time.Instant.now();
        long milliseconds = now.toEpochMilli() - trailer.getArrivedAt().toEpochMilli();
        return milliseconds / 60000.0; // Convert to minutes
    }
}
