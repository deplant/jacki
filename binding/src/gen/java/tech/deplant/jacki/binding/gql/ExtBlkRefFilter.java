package tech.deplant.jacki.binding.gql;

public record ExtBlkRefFilter(StringFilter end_lt, StringFilter file_hash, StringFilter root_hash,
    FloatFilter seq_no, ExtBlkRefFilter OR) {
}
