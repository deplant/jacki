package tech.deplant.jacki.binding.gql;

import java.util.List;

public record BlockAccountBlocks(String account_addr, String new_hash, String old_hash,
    Integer tr_count, List<BlockAccountBlocksTransactions> transactions) {
}
