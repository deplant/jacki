package tech.deplant.jacki.binding.gql;

import java.util.List;

public record AccessKey(String key, List<String> restrictToAccounts) {
}
