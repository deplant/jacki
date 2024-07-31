package tech.deplant.jacki.binding.generator.reference;

public record ArrayType(String name,
                        String type,
                        ApiType array_item,
                        String summary,
                        String description) implements ApiType {
}
