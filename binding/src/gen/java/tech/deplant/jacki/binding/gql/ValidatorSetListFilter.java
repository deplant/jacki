package tech.deplant.jacki.binding.gql;

public record ValidatorSetListFilter(StringFilter adnl_addr, StringFilter public_key,
    StringFilter weight, ValidatorSetListFilter OR) {
}
