package com.paklog.yard.domain.valueobject;


public class GPSCoordinates {
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private java.time.Instant timestamp;
    
    public double distanceTo(GPSCoordinates other) {
        if (this.latitude == null || this.longitude == null || 
            other.latitude == null || other.longitude == null) {
            return 0.0;
        }
        
        double R = 6371000; // Earth radius in meters
        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(other.latitude);
        double dLat = Math.toRadians(other.latitude - this.latitude);
        double dLon = Math.toRadians(other.longitude - this.longitude);
        
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        return R * c;
    }
}
