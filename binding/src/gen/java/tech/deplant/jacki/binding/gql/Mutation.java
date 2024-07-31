package tech.deplant.jacki.binding.gql;

import java.util.List;

public record Mutation(List<String> postRequests, Integer registerAccessKeys,
    Integer revokeAccessKeys, Integer finishOperations) {
}
