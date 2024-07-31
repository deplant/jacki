package tech.deplant.jacki.binding.gql;

public enum RempReceiptKind {
  RejectedByFullnode,

  SentToValidators,

  IncludedIntoBlock,

  IncludedIntoAcceptedBlock,

  Finalized,

  Other
}
