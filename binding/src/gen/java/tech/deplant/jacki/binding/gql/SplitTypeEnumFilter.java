package tech.deplant.jacki.binding.gql;

import java.util.List;

public record SplitTypeEnumFilter(SplitTypeEnum eq, SplitTypeEnum ne, SplitTypeEnum gt,
    SplitTypeEnum lt, SplitTypeEnum ge, SplitTypeEnum le, List<SplitTypeEnum> in,
    List<SplitTypeEnum> notIn) {
}
