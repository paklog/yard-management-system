package com.paklog.yard.domain.repository;

import com.paklog.yard.domain.aggregate.Appointment;
import com.paklog.yard.domain.valueobject.AppointmentStatus;
import java.time.Instant;
import java.util.*;

public interface AppointmentRepository {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(String id);
    List<Appointment> findAll();
    List<Appointment> findByStatus(AppointmentStatus status);
    List<Appointment> findByTrailerId(String trailerId);
    List<Appointment> findByDateRange(Instant start, Instant end);
    void deleteById(String id);
}
