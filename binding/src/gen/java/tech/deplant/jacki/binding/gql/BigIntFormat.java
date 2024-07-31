package tech.deplant.jacki.binding.gql;

/**
 * Due to GraphQL limitations big numbers are returned as a string.
     * You can specify format used to string representation for big integers.;
 */
public enum BigIntFormat {
  HEX,

  DEC
}
