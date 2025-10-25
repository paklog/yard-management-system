package com.paklog.yard.domain.event;


public class TrailerArrivedEvent extends DomainEvent {
    private String trailerId;
    private String trailerNumber;
    private java.time.Instant arrivedAt;

    private TrailerArrivedEvent(String trailerId, String trailerNumber, java.time.Instant arrivedAt) {
        this.trailerId = trailerId;
        this.trailerNumber = trailerNumber;
        this.arrivedAt = arrivedAt;
    }

    public String getTrailerId() { return trailerId; }
    public String getTrailerNumber() { return trailerNumber; }
    public java.time.Instant getArrivedAt() { return arrivedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String trailerId;
        private String trailerNumber;
        private java.time.Instant arrivedAt;

        public Builder trailerId(String trailerId) { this.trailerId = trailerId; return this; }
        public Builder trailerNumber(String trailerNumber) { this.trailerNumber = trailerNumber; return this; }
        public Builder arrivedAt(java.time.Instant arrivedAt) { this.arrivedAt = arrivedAt; return this; }

        public TrailerArrivedEvent build() {
            return new TrailerArrivedEvent(trailerId, trailerNumber, arrivedAt);
        
}
}
}
