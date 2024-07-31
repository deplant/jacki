package tech.deplant.jacki.frtest.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tech.deplant.jacki.binding.TvmSdk;
import tech.deplant.jacki.binding.TvmSdkException;
import tech.deplant.jacki.binding.loader.AbsolutePathLoader;
import tech.deplant.jacki.framework.Credentials;
import tech.deplant.jacki.framework.contract.multisig.SafeMultisigWalletContract;
import tech.deplant.jacki.framework.datatype.Address;
import tech.deplant.jacki.framework.datatype.Uint;
import tech.deplant.jacki.framework.template.*;

import java.io.IOException;
import java.math.BigInteger;

import static tech.deplant.jacki.frtest.unit.Env.COIN_ONE;

public class AckiNackiTest {

    final static String GOSH_ENDPOINT = "https://ackinacki-testnet.tvmlabs.dev/graphql";

    @BeforeAll
    public static void init_sdk_and_other_vars() throws IOException, TvmSdkException {
        TvmSdk.load();
    }

    @Test
    public void test_show_key_pairs() throws TvmSdkException {
        var sdkGosh = TvmSdk.builder()
                            .networkEndpoints(GOSH_ENDPOINT)
                            .networkQueryTimeout(300_000L)
                            .build();
        var KeyPairs = Credentials.ofRandom(sdkGosh);
        System.out.println(KeyPairs);
    }

    @Test
    public void gosh_multisig_deploy_test() throws IOException, TvmSdkException {
        var goshGiverAddress = new Address("0:c5bdf82533a8d5f89a2e15f567090f722f2ba711e6944051ab24f1bbe51465ce");
        var giverKeys = new Credentials("9aa86533452795d4f65a8fa7f74744a304d57d3f0f9f72ef1ff46b9f47f9c71e",
                "f935f680c23ddfd0a0c334b1b6d64465a4dbbf00e0ec54010b7bb60c0328f0e6");

        var sdkGosh = TvmSdk.builder()
                .networkEndpoints(GOSH_ENDPOINT)
                .networkQueryTimeout(300_000L)
                .build();
        var goshGiver = new SafeMultisigWalletContract(sdkGosh, String.valueOf(goshGiverAddress), giverKeys);



        var myKeys = Credentials.ofRandom(sdkGosh);
        System.out.println("Keys: " + myKeys);
        var deployStatement = new MultisigWalletGoshTemplate().prepareDeploy(sdkGosh,0,
                myKeys,
                new BigInteger[]{myKeys.publicKeyBigInt()},
                1);

        System.out.println("Address: " + deployStatement.toAddress());
        var goshDeployContract = deployStatement.deployWithGiver(goshGiver, COIN_ONE);
        System.out.println("Balance: " + new Uint(goshDeployContract.account().balance()).toJava());
    }
    //@Test
    public void getGoshTemplate() throws JsonProcessingException, TvmSdkException {
        var goshGiverAddress = new Address("0:c5bdf82533a8d5f89a2e15f567090f722f2ba711e6944051ab24f1bbe51465ce");
        var giverKeys = new Credentials("9aa86533452795d4f65a8fa7f74744a304d57d3f0f9f72ef1ff46b9f47f9c71e",
                "f935f680c23ddfd0a0c334b1b6d64465a4dbbf00e0ec54010b7bb60c0328f0e6");

        TvmSdk.load(new AbsolutePathLoader("C:/opt/gosh-sdk/ton_client.dll"));

        var sdkGosh = TvmSdk.builder()
                .networkEndpoints(GOSH_ENDPOINT)
                .networkQueryTimeout(300_000L)
                .build();
        //var goshGiver = new SafeMultisigWalletContract(sdkGosh, String.valueOf(goshGiverAddress), giverKeys);


        //var myKeys = Credentials.ofRandom(sdkGosh);
        //System.out.println("Keys: " + myKeys);
        var deployStatement = new SafeMultisigWalletTemplate().prepareDeploy(sdkGosh,0,
                giverKeys,
                new BigInteger[]{giverKeys.publicKeyBigInt()},
                1);

        var goshDeployContract = deployStatement.deploy();

        System.out.println("Balance: " + new Uint(goshDeployContract.account().balance()).toJava());

    }

}
