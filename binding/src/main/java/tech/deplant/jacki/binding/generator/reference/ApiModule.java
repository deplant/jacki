package tech.deplant.jacki.binding.generator.reference;

public record ApiModule(String name,
                        String summary,
                        String description,
                        ApiType[] types,
                        ApiFunction[] functions) {
}
