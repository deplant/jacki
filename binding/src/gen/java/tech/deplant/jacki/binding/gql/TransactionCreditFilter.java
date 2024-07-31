package tech.deplant.jacki.binding.gql;

public record TransactionCreditFilter(StringFilter credit, OtherCurrencyArrayFilter credit_other,
    StringFilter due_fees_collected, TransactionCreditFilter OR) {
}
