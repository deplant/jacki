package tech.deplant.jacki.binding.gql;

/**
 * This type is unstable;
 */
public record PageInfo(String startCursor, String endCursor, Boolean hasNextPage,
    Boolean hasPreviousPage) {
}
