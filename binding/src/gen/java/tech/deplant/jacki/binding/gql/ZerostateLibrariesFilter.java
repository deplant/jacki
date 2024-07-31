package tech.deplant.jacki.binding.gql;

public record ZerostateLibrariesFilter(StringFilter hash, StringFilter lib,
    StringArrayFilter publishers, ZerostateLibrariesFilter OR) {
}
