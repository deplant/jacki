package tech.deplant.jacki.binding.gql;

import java.util.List;

/**
 * This type is unstable;
 */
public record BlockchainMessagesConnection(List<BlockchainMessageEdge> edges, PageInfo pageInfo) {
}
