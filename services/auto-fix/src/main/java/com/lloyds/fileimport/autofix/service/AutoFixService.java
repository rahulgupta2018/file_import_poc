package com.lloyds.fileimport.autofix.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

/**
 * Applies automatic corrections to validation errors where a deterministic fix is possible.
 */
@ApplicationScoped
public class AutoFixService {

    private static final Logger LOG = Logger.getLogger(AutoFixService.class);

    /**
     * Attempts to auto-fix the given value based on the rule that flagged it.
     *
     * @param fieldName  the field that failed validation
     * @param rawValue   the original value
     * @param ruleCode   the rule code that was violated
     * @return the corrected value, or the original if no fix is applicable
     */
    public String fix(String fieldName, String rawValue, String ruleCode) {
        LOG.debugf("AutoFix requested: field=%s, value=%s, rule=%s", fieldName, rawValue, ruleCode);
        // TODO: implement auto-fix strategies per rule code
        return rawValue;
    }
}
