package tech.deplant.jacki.binding.generator.reference;

public record StructType(String name,
                         String type,
                         ApiType[] struct_fields,
                         String summary,
                         String description) implements ApiType {
}
