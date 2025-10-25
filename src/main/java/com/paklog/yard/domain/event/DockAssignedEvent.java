package com.paklog.yard.domain.event;


public class DockAssignedEvent extends DomainEvent {
    private String trailerId;
    private String dockId;
    private java.time.Instant assignedAt;

    private DockAssignedEvent(String trailerId, String dockId, java.time.Instant assignedAt) {
        this.trailerId = trailerId;
        this.dockId = dockId;
        this.assignedAt = assignedAt;
    }

    public String getTrailerId() { return trailerId; }
    public String getDockId() { return dockId; }
    public java.time.Instant getAssignedAt() { return assignedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String trailerId;
        private String dockId;
        private java.time.Instant assignedAt;

        public Builder trailerId(String trailerId) { this.trailerId = trailerId; return this; }
        public Builder dockId(String dockId) { this.dockId = dockId; return this; }
        public Builder assignedAt(java.time.Instant assignedAt) { this.assignedAt = assignedAt; return this; }

        public DockAssignedEvent build() {
            return new DockAssignedEvent(trailerId, dockId, assignedAt);
        
}
}
}
