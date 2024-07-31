package tech.deplant.jacki.binding.gql;

/**
 * Block create fees;
 */
public record ConfigP14Filter(StringFilter basechain_block_fee, StringFilter masterchain_block_fee,
    ConfigP14Filter OR) {
}
