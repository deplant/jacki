package tech.deplant.jacki.binding.generator.reference;

public record ApiFunction(String name,
                          String summary,
                          String description,
                          ApiType[] params,
                          ApiType result,
                          String errors) {
}
