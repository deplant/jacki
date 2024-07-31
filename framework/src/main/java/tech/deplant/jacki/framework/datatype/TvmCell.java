package tech.deplant.jacki.framework.datatype;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import tech.deplant.jacki.binding.Abi;
import tech.deplant.jacki.binding.Boc;
import tech.deplant.jacki.binding.TvmSdk;
import tech.deplant.jacki.binding.TvmSdkException;


/**
 * The type Tvm cell.
 */
public record TvmCell(String cellBoc) implements AbiValue<String> {

	/**
	 * The constant EMPTY.
	 */
	public static final TvmCell EMPTY = new TvmCell("te6ccgEBAQEAAgAAAA==");

	/**
	 * Builder tvm builder.
	 *
	 * @return the tvm builder
	 */
	public static TvmBuilder builder() {
		return new TvmBuilder();
	}

	public String bocHash(int contextId) throws TvmSdkException {
		return TvmSdk.await(Boc.getBocHash(contextId, cellBoc())).hash();
	}

	/**
	 * Decode json node.
	 *
	 * @param contextId the context id
	 * @param types     the types
	 * @return the json node
	 * @throws TvmSdkException the ever sdk exception
	 */
	public JsonNode decode(int contextId, Abi.AbiParam[] types) throws TvmSdkException {
		return TvmSdk.await(Abi.decodeBoc(contextId, types, cellBoc(), true)).data();
	}

	/**
	 * Decode json node.
	 *
	 * @param contextId the context id
	 * @param types     the types
	 * @return the json node
	 * @throws TvmSdkException the ever sdk exception
	 */
	public JsonNode decode(int contextId, String[] types) throws TvmSdkException {
		Abi.AbiParam[] params = new Abi.AbiParam[types.length];
		for (int i = 0; i < types.length; i++) {
			params[i] = new Abi.AbiParam(String.valueOf(i), types[i], null, false);
		}
		return decode(contextId, params);
	}

	/**
	 * Decode json node.
	 *
	 * @param contextId the context id
	 * @param types     the types
	 * @return the json node
	 * @throws TvmSdkException the ever sdk exception
	 */
	public JsonNode decode(int contextId, AbiType[] types) throws TvmSdkException {
		Abi.AbiParam[] params = new Abi.AbiParam[types.length];
		for (int i = 0; i < types.length; i++) {
			params[i] = new Abi.AbiParam(String.valueOf(i), types[i].abiName(), null, false);
		}
		return decode(contextId, params);
	}

	/**
	 * Decode and get json node.
	 *
	 * @param contextId the context id
	 * @param types     the types
	 * @param index     the index
	 * @return the json node
	 * @throws TvmSdkException the ever sdk exception
	 */
	public JsonNode decodeAndGet(int contextId, String[] types, int index) throws TvmSdkException {
		var result = decode(contextId, types);
		return result.get(String.valueOf(index));
	}

	@Override
	public String toString() {
		return cellBoc();
	}

	@Override
	public String toJava() {
		return toString();
	}

	@JsonValue
	public String toABI() {
		return cellBoc();
	}

	@Override
	public AbiType type() {
		return new AbiType(AbiTypePrefix.STRING, 0, false);
	}

}
