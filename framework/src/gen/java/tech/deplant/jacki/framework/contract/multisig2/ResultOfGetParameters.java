package tech.deplant.jacki.framework.contract.multisig2;

import java.lang.Integer;
import java.math.BigInteger;

public record ResultOfGetParameters(Integer maxQueuedTransactions, Integer maxCustodianCount,
    BigInteger expirationTime, BigInteger minValue, Integer requiredTxnConfirms,
    Integer requiredUpdConfirms) {
}
