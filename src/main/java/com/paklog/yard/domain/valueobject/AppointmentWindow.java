package com.paklog.yard.domain.valueobject;

import java.time.Instant;

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

    // Getters
    public Instant getStartTime() { return startTime; }
    public Instant getEndTime() { return endTime; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public String getTimeZone() { return timeZone; }

    // Setters
    public void setStartTime(Instant startTime) { this.startTime = startTime; }
    public void setEndTime(Instant endTime) { this.endTime = endTime; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public void setTimeZone(String timeZone) { this.timeZone = timeZone; }
}
