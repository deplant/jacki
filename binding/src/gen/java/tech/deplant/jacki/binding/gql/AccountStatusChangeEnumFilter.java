package tech.deplant.jacki.binding.gql;

import java.util.List;

public record AccountStatusChangeEnumFilter(AccountStatusChangeEnum eq, AccountStatusChangeEnum ne,
    AccountStatusChangeEnum gt, AccountStatusChangeEnum lt, AccountStatusChangeEnum ge,
    AccountStatusChangeEnum le, List<AccountStatusChangeEnum> in,
    List<AccountStatusChangeEnum> notIn) {
}
