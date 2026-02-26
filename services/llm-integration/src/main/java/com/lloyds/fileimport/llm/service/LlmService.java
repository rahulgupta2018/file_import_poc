package com.lloyds.fileimport.llm.service;

import com.lloyds.fileimport.llm.client.LlmClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

/**
 * Service that orchestrates calls to the LLM backend via {@link LlmClient}.
 */
@ApplicationScoped
public class LlmService {

    private static final Logger LOG = Logger.getLogger(LlmService.class);

    @Inject
    @RestClient
    LlmClient llmClient;

    /**
     * Sends a prompt to the LLM and returns the raw response.
     *
     * @param prompt the user prompt
     * @return the LLM response as a JSON string
     */
    public String complete(String prompt) {
        LOG.debugf("Sending prompt to LLM (%d chars)", prompt.length());
        String requestBody = """
                {
                  "model": "llama3",
                  "messages": [{"role": "user", "content": "%s"}],
                  "temperature": 0.2
                }
                """.formatted(prompt.replace("\"", "\\\""));
        return llmClient.chatCompletion(requestBody);
    }
}
