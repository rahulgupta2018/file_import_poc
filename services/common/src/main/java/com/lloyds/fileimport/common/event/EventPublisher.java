package com.lloyds.fileimport.common.event;

/**
 * Abstraction for event publishing — implemented as in-process CDI events (dev) or Pub/Sub (prod).
 */
public interface EventPublisher {

    void publish(ImportEvent event);
}
