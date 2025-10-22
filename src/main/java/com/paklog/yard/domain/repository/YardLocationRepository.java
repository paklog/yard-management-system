package com.paklog.yard.domain.repository;

import com.paklog.yard.domain.aggregate.YardLocation;
import com.paklog.yard.domain.valueobject.LocationType;
import java.util.*;

public interface YardLocationRepository {
    YardLocation save(YardLocation location);
    Optional<YardLocation> findById(String id);
    List<YardLocation> findAll();
    List<YardLocation> findByLocationType(LocationType type);
    List<YardLocation> findAvailableLocations();
    void deleteById(String id);
}
