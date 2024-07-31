package tech.deplant.jacki.binding.gql;

public record MsgEnvelopeFilter(StringFilter cur_addr, StringFilter fwd_fee_remaining,
    StringFilter msg_id, StringFilter next_addr, MsgEnvelopeFilter OR) {
}
