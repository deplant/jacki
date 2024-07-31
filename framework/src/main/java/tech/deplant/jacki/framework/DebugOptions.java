package tech.deplant.jacki.framework;

/**
 * The type Debug options.
 */
public record DebugOptions(boolean enabled, long timeout, boolean throwErrors, long transactionMaxCount, ContractAbi... treeAbis) {
}
