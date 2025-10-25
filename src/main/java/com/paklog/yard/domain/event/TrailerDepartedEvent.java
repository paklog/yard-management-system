package com.paklog.yard.domain.event;


public class TrailerDepartedEvent extends DomainEvent {
    private String trailerId;
    private java.time.Instant departedAt;
    private Long dwellTimeMinutes;

    private TrailerDepartedEvent(String trailerId, java.time.Instant departedAt, Long dwellTimeMinutes) {
        this.trailerId = trailerId;
        this.departedAt = departedAt;
        this.dwellTimeMinutes = dwellTimeMinutes;
    }

    public String getTrailerId() { return trailerId; }
    public java.time.Instant getDepartedAt() { return departedAt; }
    public Long getDwellTimeMinutes() { return dwellTimeMinutes; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String trailerId;
        private java.time.Instant departedAt;
        private Long dwellTimeMinutes;

        public Builder trailerId(String trailerId) { this.trailerId = trailerId; return this; }
        public Builder departedAt(java.time.Instant departedAt) { this.departedAt = departedAt; return this; }
        public Builder dwellTimeMinutes(Long dwellTimeMinutes) { this.dwellTimeMinutes = dwellTimeMinutes; return this; }

        public TrailerDepartedEvent build() {
            return new TrailerDepartedEvent(trailerId, departedAt, dwellTimeMinutes);
        
}
}
}
