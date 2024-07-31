package tech.deplant.jacki.unit;

import org.junit.jupiter.api.Test;
import tech.deplant.jacki.binding.TvmSdk;
import tech.deplant.jacki.binding.TvmSdkException;
import tech.deplant.jacki.binding.loader.AbsolutePathLoader;

public class TestEnv {
	public static final String NODESE_URL = "https://nodese.truequery.tech";
	public static final String NODESE_ENDPOINT = "https://nodese.truequery.tech/graphql";

	static void loadEverSdk() {
		TvmSdk.load();
	}

	static void loadAckiNackiSdk() {
		TvmSdk.load(new AbsolutePathLoader("c:/opt/gosh-sdk/ton_client.dll"));
	}

	static int newContextEmpty() {
		try {
			return TvmSdk.createDefault();
		} catch (TvmSdkException e) {
			throw new RuntimeException(e);
		}
	}

	static int newContext() {
		try {
			return TvmSdk.createWithEndpoint(NODESE_ENDPOINT);
		} catch (TvmSdkException e) {
			throw new RuntimeException(e);
		}
	}


	@Test
	public void test_method()
	{
		byte num = (byte)127;
		num++;
		System.out.println(num);
	}

}
