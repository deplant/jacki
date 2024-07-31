package tech.deplant.jacki.frtest.unit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import tech.deplant.jacki.binding.TvmSdkException;
import tech.deplant.jacki.framework.Credentials;
import tech.deplant.jacki.framework.LocalConfig;
import tech.deplant.jacki.framework.OnchainConfig;
import tech.deplant.jacki.framework.contract.AbstractContract;
import tech.deplant.jacki.framework.datatype.Address;
import tech.deplant.jacki.framework.template.SafeMultisigWalletTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tech.deplant.jacki.frtest.unit.Env.INIT;
import static tech.deplant.jacki.frtest.unit.Env.SDK_EMPTY;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Execution(ExecutionMode.CONCURRENT)
public class ConfigTests {

	@BeforeAll
	public static void init_sdk_and_other_vars() throws IOException, TvmSdkException {
		INIT();
	}

	@Test
	public void serialized_then_deserialized_onchain_configs_are_equal() throws IOException, TvmSdkException {
		var keys = Credentials.ofRandom(SDK_EMPTY);
		var contract = new AbstractContract(SDK_EMPTY, Address.ZERO.toString(), SafeMultisigWalletTemplate.DEFAULT_ABI(), Credentials.NONE);
		var conf = OnchainConfig.EMPTY("config/onchain-config.json");
		conf.addKeys("test_keys", keys);
		conf.addContract("test_contract", contract);
		conf = OnchainConfig.LOAD("config/onchain-config.json");
		assertEquals(keys.publicKey(), conf.keys("test_keys").publicKey());
		assertEquals(contract.abi().json(), conf.contract(AbstractContract.class, SDK_EMPTY, "test_contract").abi().json());
	}

	@Test
	public void serialized_then_deserialized_local_configs_are_equal() throws IOException, TvmSdkException {
var local = LocalConfig.EMPTY("config/local-config.json", "C:/everscale/TON-Solidity-Compiler/build/solc/Release/solc",
		                             "C:/everscale/TVM-linker/target/release/tvm_linker",
		                             "C:/everscale/TON-Solidity-Compiler/lib/stdlib_sol.tvm",
		                             "C:/idea_git/iris-contracts-core/src/main/solidity",
		                             "C:/idea_git/iris-contracts-core/src/main/resources/artifacts");

		local = LocalConfig.LOAD("config/local-config.json");
		assertEquals("C:/everscale/TVM-linker/target/release/tvm_linker", local.info().linker().linkerPath());
	}

}
