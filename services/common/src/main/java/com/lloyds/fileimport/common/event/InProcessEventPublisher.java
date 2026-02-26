package com.lloyds.fileimport.common.event;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

/**
 * In-process synchronous event publisher for local development.
 * Uses Quarkus CDI events — subscribers use {@code @Observes ImportEvent}.
 */
@ApplicationScoped
@IfBuildProfile("dev")
public class InProcessEventPublisher implements EventPublisher {

    private static final Logger LOG = Logger.getLogger(InProcessEventPublisher.class);

    @Inject
    Event<ImportEvent> cdiEvent;

    @Override
    public void publish(ImportEvent event) {
        LOG.infof("[DEV] Publishing event: %s for import %s", event.type(), event.importId());
        cdiEvent.fire(event);
    }
}
