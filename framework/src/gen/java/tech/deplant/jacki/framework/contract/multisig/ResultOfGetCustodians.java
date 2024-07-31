package tech.deplant.jacki.framework.contract.multisig;

import java.lang.Object;
import java.lang.String;
import java.util.Map;

public record ResultOfGetCustodians(Map<String, Object>[] custodians) {
}
