package tech.deplant.jacki.binding.generator.reference;

public record OptionalType(String name,
                           String type,
                           ApiType optional_inner,
                           String summary,
                           String description) implements ApiType {
}
