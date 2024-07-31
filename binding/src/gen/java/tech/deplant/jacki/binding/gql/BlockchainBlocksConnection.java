package tech.deplant.jacki.binding.gql;

import java.util.List;

/**
 * This type is unstable;
 */
public record BlockchainBlocksConnection(List<BlockchainBlocksEdge> edges, PageInfo pageInfo) {
}
