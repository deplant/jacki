package tech.deplant.jacki.binding.gql;

public record ConfigP42Filter(ConfigP42PayoutsArrayFilter payouts, StringFilter threshold,
    ConfigP42Filter OR) {
}
