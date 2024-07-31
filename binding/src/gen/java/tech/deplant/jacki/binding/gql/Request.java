package tech.deplant.jacki.binding.gql;

/**
 * Request with external inbound message;
 */
public record Request(String id, String body, Float expireAt) {
}
