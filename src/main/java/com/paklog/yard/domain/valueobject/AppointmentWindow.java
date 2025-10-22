package com.paklog.yard.domain.valueobject;

import lombok.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentWindow {
    private Instant startTime;
    private Instant endTime;
    private Integer durationMinutes;
    private String timeZone;
    
    public boolean isWithinWindow(Instant timestamp) {
        return !timestamp.isBefore(startTime) && !timestamp.isAfter(endTime);
    }
    
    public boolean overlaps(AppointmentWindow other) {
        return !this.endTime.isBefore(other.startTime) && 
               !other.endTime.isBefore(this.startTime);
    }
}
