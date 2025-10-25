package com.paklog.yard.domain.event;


public class LoadingCompletedEvent extends DomainEvent {
    private String trailerId;
    private java.time.Instant completedAt;

    private LoadingCompletedEvent(String trailerId, java.time.Instant completedAt) {
        this.trailerId = trailerId;
        this.completedAt = completedAt;
    }

    public String getTrailerId() { return trailerId; }
    public java.time.Instant getCompletedAt() { return completedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String trailerId;
        private java.time.Instant completedAt;

        public Builder trailerId(String trailerId) { this.trailerId = trailerId; return this; }
        public Builder completedAt(java.time.Instant completedAt) { this.completedAt = completedAt; return this; }

        public LoadingCompletedEvent build() {
            return new LoadingCompletedEvent(trailerId, completedAt);
        
}
}
}
