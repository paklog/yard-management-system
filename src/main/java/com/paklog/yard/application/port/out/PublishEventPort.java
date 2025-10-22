package com.paklog.yard.application.port.out;

import com.paklog.yard.domain.event.DomainEvent;

public interface PublishEventPort {
    void publishEvent(DomainEvent event);
}
