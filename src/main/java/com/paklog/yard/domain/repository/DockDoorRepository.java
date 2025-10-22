package com.paklog.yard.domain.repository;

import com.paklog.yard.domain.aggregate.DockDoor;
import com.paklog.yard.domain.valueobject.DockStatus;
import java.util.*;

public interface DockDoorRepository {
    DockDoor save(DockDoor dock);
    Optional<DockDoor> findById(String id);
    List<DockDoor> findAll();
    List<DockDoor> findByStatus(DockStatus status);
    List<DockDoor> findAvailableDocks();
    void deleteById(String id);
}
