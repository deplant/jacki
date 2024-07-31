package tech.deplant.jacki.binding.gql;

public record BlockLimitsFilter(BlockLimitsBytesFilter bytes, BlockLimitsGasFilter gas,
    BlockLimitsLtDeltaFilter lt_delta, BlockLimitsFilter OR) {
}
