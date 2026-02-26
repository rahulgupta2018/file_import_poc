package com.lloyds.fileimport.llm.client;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * MicroProfile REST Client for an OpenAI-compatible chat completions API.
 */
@Path("/v1/chat/completions")
@RegisterRestClient(configKey = "llm-api")
public interface LlmClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    String chatCompletion(String requestBody);
}
