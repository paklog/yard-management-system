package com.paklog.yard.domain.event;


public class AppointmentScheduledEvent extends DomainEvent {
    private String appointmentId;
    private String trailerId;
    private String dockId;
    private java.time.Instant scheduledTime;

    private AppointmentScheduledEvent(String appointmentId, String trailerId, String dockId, java.time.Instant scheduledTime) {
        this.appointmentId = appointmentId;
        this.trailerId = trailerId;
        this.dockId = dockId;
        this.scheduledTime = scheduledTime;
    }

    public String getAppointmentId() { return appointmentId; }
    public String getTrailerId() { return trailerId; }
    public String getDockId() { return dockId; }
    public java.time.Instant getScheduledTime() { return scheduledTime; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String appointmentId;
        private String trailerId;
        private String dockId;
        private java.time.Instant scheduledTime;

        public Builder appointmentId(String appointmentId) { this.appointmentId = appointmentId; return this; }
        public Builder trailerId(String trailerId) { this.trailerId = trailerId; return this; }
        public Builder dockId(String dockId) { this.dockId = dockId; return this; }
        public Builder scheduledTime(java.time.Instant scheduledTime) { this.scheduledTime = scheduledTime; return this; }

        public AppointmentScheduledEvent build() {
            return new AppointmentScheduledEvent(appointmentId, trailerId, dockId, scheduledTime);
        
}
}
}
