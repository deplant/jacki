package tech.deplant.jacki.binding.gql;

public record BlockMasterShardHashesFilter(BlockMasterShardHashesDescrFilter descr,
    StringFilter shard, IntFilter workchain_id, BlockMasterShardHashesFilter OR) {
}
