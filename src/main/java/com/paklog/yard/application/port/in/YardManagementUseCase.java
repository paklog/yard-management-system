package com.paklog.yard.application.port.in;

import com.paklog.yard.application.command.*;
import com.paklog.yard.domain.aggregate.Trailer;
import java.util.List;

public interface YardManagementUseCase {
    String checkInTrailer(CheckInTrailerCommand command);
    void assignDock(AssignDockCommand command);
    String scheduleAppointment(ScheduleAppointmentCommand command);
    void checkOutTrailer(CheckOutTrailerCommand command);
    Trailer getTrailer(String trailerId);
    List<Trailer> getActiveTrailers();
}
