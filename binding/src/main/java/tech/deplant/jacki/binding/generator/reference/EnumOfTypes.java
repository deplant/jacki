package tech.deplant.jacki.binding.generator.reference;

public record EnumOfTypes(String name,
                          String type,
                          ApiType[] enum_types,
                          String summary,
                          String description) implements ApiType {
}
