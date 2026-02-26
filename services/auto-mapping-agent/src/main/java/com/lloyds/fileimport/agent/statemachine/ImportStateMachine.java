package com.lloyds.fileimport.agent.statemachine;

import com.lloyds.fileimport.common.model.ImportStatus;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

/**
 * Manages state transitions for an import workflow.
 */
@ApplicationScoped
public class ImportStateMachine {

    private static final Logger LOG = Logger.getLogger(ImportStateMachine.class);

    private ImportStatus currentStatus = ImportStatus.CREATED;

    /**
     * Returns the current state.
     */
    public ImportStatus currentState() {
        return currentStatus;
    }

    /**
     * Attempts to transition from the current state to the target state.
     *
     * @param target the desired next state
     * @return true if the transition was accepted, false otherwise
     */
    public boolean transition(ImportStatus target) {
        LOG.infof("State transition requested: %s -> %s", currentStatus, target);
        // TODO: implement valid-transition lookup table
        this.currentStatus = target;
        return true;
    }
}
