package tech.deplant.jacki.binding.generator.reference;

public record StringType(String name,
                         String type,
                         String summary,
                         String description) implements ApiType {
}
