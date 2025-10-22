package com.paklog.yard.infrastructure.kafka;

import com.paklog.yard.application.port.out.PublishEventPort;
import com.paklog.yard.domain.event.DomainEvent;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher implements PublishEventPort {
    
    private final KafkaTemplate<String, CloudEvent> kafkaTemplate;
    
    @Value("${yard.events.topic:yard.events}")
    private String topic;
    
    @Override
    public void publishEvent(DomainEvent event) {
        try {
            CloudEvent cloudEvent = CloudEventBuilder.v1()
                .withId(UUID.randomUUID().toString())
                .withType("com.paklog.yard." + event.getEventType())
                .withSource(URI.create("//yard-management"))
                .withTime(OffsetDateTime.now())
                .withData("application/json", event.toString().getBytes())
                .build();
            
            kafkaTemplate.send(topic, event.getEventId(), cloudEvent);
            log.debug("Published event: {}", event.getEventType());
        } catch (Exception e) {
            log.error("Failed to publish event: {}", event.getEventType(), e);
        }
    }
}
