package tech.deplant.jacki.binding.gql;

public record BlockAccountBlocksTransactionsFilter(StringFilter lt, StringFilter total_fees,
    OtherCurrencyArrayFilter total_fees_other, StringFilter transaction_id,
    BlockAccountBlocksTransactionsFilter OR) {
}
