package tech.deplant.jacki.binding.gql;

public record OtherCurrencyFilter(FloatFilter currency, StringFilter value,
    OtherCurrencyFilter OR) {
}
