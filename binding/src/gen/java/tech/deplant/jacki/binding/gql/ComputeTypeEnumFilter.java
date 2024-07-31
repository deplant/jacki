package tech.deplant.jacki.binding.gql;

import java.util.List;

public record ComputeTypeEnumFilter(ComputeTypeEnum eq, ComputeTypeEnum ne, ComputeTypeEnum gt,
    ComputeTypeEnum lt, ComputeTypeEnum ge, ComputeTypeEnum le, List<ComputeTypeEnum> in,
    List<ComputeTypeEnum> notIn) {
}
