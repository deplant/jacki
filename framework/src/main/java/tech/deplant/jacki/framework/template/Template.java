package tech.deplant.jacki.framework.template;

import com.fasterxml.jackson.databind.JsonNode;
import tech.deplant.jacki.binding.Boc;
import tech.deplant.jacki.binding.TvmSdk;
import tech.deplant.jacki.binding.TvmSdkException;
import tech.deplant.jacki.framework.*;

/**
 * The interface Template.
 */
public interface Template {

	/**
	 * The constant logger.
	 */
	System.Logger logger = System.getLogger(Template.class.getName());

	/**
	 * Abi contract abi.
	 *
	 * @return the contract abi
	 */
	ContractAbi abi();

	/**
	 * Tvc tvc.
	 *
	 * @return the tvc
	 */
	Tvc tvc();

//	default String calculateAddress(Sdk sdk,
//	                                Map<String, Object> initialData,
//	                                Credentials credentials) throws EverSdkException {
//		String address = Address.ofFutureDeploy(sdk, new AbstractTemplate(abi(), tvc()), 0, initialData, credentials);
//		logger.log(System.Logger.Level.INFO, () -> "Future address: " + address);
//		return address;
//	}


	/**
	 * Decode initial data json node.
	 *
	 * @param sdk the sdk
	 * @return the json node
	 * @throws TvmSdkException the ever sdk exception
	 */
	default JsonNode decodeInitialData(int sdk) throws TvmSdkException {
		return tvc().decodeInitialData(sdk, abi());
	}

	/**
	 * Decode initial pubkey string.
	 *
	 * @param sdk the sdk
	 * @return the string
	 * @throws TvmSdkException the ever sdk exception
	 */
	default String decodeInitialPubkey(int sdk) throws TvmSdkException {
		return tvc().decodeInitialPubkey(sdk, abi());
	}

	/**
	 * Address from encoded tvc string.
	 *
	 * @param sdk the sdk
	 * @return the string
	 * @throws TvmSdkException the ever sdk exception
	 */
	default String addressFromEncodedTvc(int sdk) throws TvmSdkException {
		return String.format("0:%s", TvmSdk.await(Boc.getBocHash(sdk, tvc().base64String())).hash());
	}

	/**
	 * Is deployed boolean.
	 *
	 * @param sdk the sdk
	 * @return the boolean
	 * @throws TvmSdkException the ever sdk exception
	 */
	default boolean isDeployed(int sdk) throws TvmSdkException {
		return Account.ofAddress(sdk, addressFromEncodedTvc(sdk)).isActive();
	}

}
