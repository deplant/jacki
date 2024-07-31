package tech.deplant.jacki.framework.contract;

import com.fasterxml.jackson.annotation.JsonCreator;
import tech.deplant.jacki.binding.Abi;
import tech.deplant.jacki.framework.ContractAbi;
import tech.deplant.jacki.framework.Credentials;
import tech.deplant.jacki.framework.FunctionHandle;
import tech.deplant.jacki.framework.datatype.Address;

import java.math.BigInteger;

/**
 * The type Giver contract.
 */
public abstract class GiverContract extends AbstractContract {

	/**
	 * The Logger.
	 */
	System.Logger logger = System.getLogger(GiverContract.class.getName());

	/**
	 * Instantiates a new Giver contract.
	 *
	 * @param sdk         the sdk
	 * @param address     the address
	 * @param abi         the abi
	 * @param credentials the credentials
	 */
	@JsonCreator
	public GiverContract(int sdk, String address, ContractAbi abi, Credentials credentials) {
		super(sdk, address, abi, credentials);
	}

	/**
	 * Instantiates a new Giver contract.
	 *
	 * @param sdk         the sdk
	 * @param address     the address
	 * @param abi         the abi
	 * @param signer the signer
	 */
	public GiverContract(int sdk, String address, ContractAbi abi, Abi.Signer signer) {
		super(sdk, new Address(address), abi, signer);
	}

	/**
	 * Send transaction function handle.
	 *
	 * @param dest   the dest
	 * @param value  the value
	 * @param bounce the bounce
	 * @return the function handle
	 */
	public abstract FunctionHandle<Void> sendTransaction(Address dest, BigInteger value, Boolean bounce);

	/**
	 * Give function handle.
	 *
	 * @param to    the to
	 * @param value the value
	 * @return the function handle
	 */
	public FunctionHandle<Void> give(Address to, BigInteger value) {
		logger.log(System.Logger.Level.INFO, "Giver called!");
		return sendTransaction(to, value, false);
	}

}
