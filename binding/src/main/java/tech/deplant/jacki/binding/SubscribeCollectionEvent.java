package tech.deplant.jacki.binding;

import java.util.Map;

public record SubscribeCollectionEvent(Map<String, Object> result) implements ExternalEvent {
}
