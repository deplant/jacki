package tech.deplant.jacki.binding;

import com.fasterxml.jackson.databind.JsonNode;

public record SubscribeEvent(JsonNode result) implements ExternalEvent {
}
