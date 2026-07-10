package com.carmanagement.agentic.agents;

import dev.langchain4j.cdi.spi.RegisterSimpleAgent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Agent that determines what cleaning services to request.
 */
@RegisterSimpleAgent(
    name = "cleaning-agent",
    description = "Cleaning specialist. Determines what cleaning services are needed.",
    chatModelName = "chat-model",
    chatMemoryName = "cleaning-agent-memory",
    toolNames = { "cleaning-tool" }, 
    outputKey = "analysisResult",
    scope = ApplicationScoped.class
)
public interface CleaningAgent {

    @SystemMessage("""
        You handle intake for the cleaning department of a car rental company.
    """)
    @UserMessage("""
        Taking into account all provided feedback, determine if the car needs a cleaning.
        If the feedback indicates the car is dirty, has stains, or any other cleanliness issues,
        call the provided tool and recommend appropriate cleaning services (exterior wash, interior cleaning, waxing, detailing).
        Be specific about what services are needed.
        If no specific cleaning request is provided, request a standard exterior wash.

        Car Information:
        Make: {{carMake}}
        Model: {{carModel}}
        Year: {{carYear}}
        Car Number: {{carNumber}}
        
        Cleaning Request:
        {{cleaningRequest}}
    """)
    String processCleaning(
        @V("carMake") String carMake,
        @V("carModel") String carModel,
        @V("carYear") Integer carYear,
        @V("carNumber") Integer carNumber,
        @V("cleaningRequest") String cleaningRequest
    );
}
