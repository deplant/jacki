# Jacki

[![JDK version](https://img.shields.io/badge/Java-22-green.svg)](https://shields.io/)
[![SDK version](https://img.shields.io/badge/TVM%20SDK-v2.0.0.an-orange))](https://github.com/tvmlabs/tvm-sdk)
[![License](https://img.shields.io/badge/License-Apache%202.0-brown.svg)](https://shields.io/)
[![Channel on Telegram](https://img.shields.io/badge/chat-on%20telegram-9cf.svg)](https://t.me/deplant_news_en)
[![javadoc](https://javadoc.io/badge2/tech.deplant.jacki/tvm-api/javadoc.svg)](https://javadoc.io/doc/tech.deplant.jacki/tvm-api)


**Jacki** is a feature-rich framework for smart-contracts development, testing & accessing 
TVM-compatible blockchains like [GOSH](https://gosh.sh/). 

## Features

* Auto-generation of Java smart contract wrapper classes from ABI+TVC artifacts. Easy way to test, deploy and interact with TVM smart contracts.
* Auto-conversion of ABI types to Java native types for any input/output
* Out-of-the-box support of Multisig Wallets, TIP 3.x FT tokens, TIP 4.x NFT tokens, EverNode-SE Givers
* Polymorphic inheritance for givers and multisigs for easy switch between test and prod environments
* Access to transaction tree of complex calls
* Complete typed (all types, all methods) implementation of latest [TVM-SDK](https://github.com/tvmlabs/tvm-sdk/blob/main/docs/SUMMARY.md) JSON-RPC client API
* Pluggable TVM-SDK native library support (no rebuild needed, just plug-in SDK lib you like with special Loaders)

## Contents

<!-- TOC -->
* [Jacki](#jacki)
  * [Features](#features)
  * [Contents](#contents)
  * [Quick start](#quick-start)
    * [Prerequisites (Java)](#prerequisites-java)
    * [Prerequisites (Kotlin)](#prerequisites-kotlin)
    * [Add Jacki to your Maven/Gradle setup:](#add-jacki-to-your-mavengradle-setup)
    * [Deploy your first contract](#deploy-your-first-contract)
  * [Examples and Guides](#examples-and-guides)
    * [Setting up SDK](#setting-up-sdk)
      * [Loading TVM-SDK library](#loading-tvm-sdk-library)
      * [Creating config context and specifying endpoints](#creating-config-context-and-specifying-endpoints)
      * [Configuring SDK with Builder](#configuring-sdk-with-builder)
    * [Calling TVM-SDK methods](#calling-tvm-sdk-methods)
    * [Adding ABI, TVC & other artifacts](#adding-abi-tvc--other-artifacts)
    * [Crypto](#crypto)
      * [Creating a random keypair](#creating-a-random-keypair)
      * [Creating a random seed](#creating-a-random-seed)
      * [Deriving keys from seed](#deriving-keys-from-seed)
      * [Using existing keys & seeds](#using-existing-keys--seeds)
      * [Using "Signing Box" object](#using-signing-box-object)
    * [Smart-contracts](#smart-contracts)
      * [Interacting with already deployed contracts](#interacting-with-already-deployed-contracts)
      * [Accessing contract account metadata](#accessing-contract-account-metadata)
    * [Contract Generation](#contract-generation)
      * [Calling generator for your artifacts](#calling-generator-for-your-artifacts)
      * [Accessing your newly generated contract wrapper](#accessing-your-newly-generated-contract-wrapper)
      * [Accessing generated function wrappers](#accessing-generated-function-wrappers)
      * [Calling Functions in various ways](#calling-functions-in-various-ways)
      * [Deploying new contracts](#deploying-new-contracts)
    * [Using wallets and other standard contract wrappers](#using-wallets-and-other-standard-contract-wrappers)
      * [Sending Internal Message from Multisig Wallet](#sending-internal-message-from-multisig-wallet)
      * [Encoding as Payload](#encoding-as-payload)
    * [Deploying smart-contracts](#deploying-smart-contracts)
      * [Accessing Template](#accessing-template)
      * [Prepared deployment set](#prepared-deployment-set)
      * [Variations of running deploy](#variations-of-running-deploy)
      * [Switching Givers](#switching-givers)
      * [Offline deployment](#offline-deployment)
    * [Subsriptions](#subsriptions)
    * [Currency](#currency)
    * [Connecting to logging](#connecting-to-logging)
  * [Getting Help](#getting-help)
<!-- TOC -->

## Quick start

### Prerequisites (Java)

* Install **JDK 22** ([downloaded here](https://adoptium.net/temurin/releases/?version=22))
* (Optional) Install **EverNode-SE** ([installation guide here](https://github.com/everx-labs/evernode-se))

**Note:** EverNode-SE needed only for quick start example. It's not a requirement

### Prerequisites (Kotlin)

* Install **Kotlin 2.0.0**
* Install **Gradle 8.8+**
* Make sure to setup Gradle 8.8 and JDK 22 usage both for compilation and runtime

### Add Jacki to your Maven/Gradle setup:

* Gradle

```groovy
dependencies {
    implementation 'tech.deplant.jacki:tvm-api:1.0.0'
}
```

* Maven

```xml

<dependency>
    <groupId>tech.deplant.jacki</groupId>
    <artifactId>tvm-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Deploy your first contract

```java
// initialize TVM-SDK library
TvmSdk.load(); 
// create config context, save its id
int contextId = TvmSdk.createWithEndpoint("http://localhost/graphql").orElseThrow();
// creates random pair of keys
var keys = Credentials.ofRandom(contextId); 
// use it to deploy a new contract
var contract = new SafeMultisigWalletTemplate()
        .prepareDeploy(contextId,0, keys, new BigInteger[]{keys.publicKeyBigInt()}, 1)
        .deployWithGiver(EverOSGiver.V2(contextId), CurrencyUnit.VALUE(COIN, "1"));
// get the contract info
System.out.println(contract.account().id() + " is active: " + contract.account().isActive());
```

## Examples and Guides

### Setting up SDK

**SDK** setup consists of two steps:
1. Loading TVM-SDK library (should be done once)
2. Creating context/session with certain config (should be done for every new endpoint or config change)

Both steps are described below.

#### Loading TVM-SDK library

To load TVM-SDK connection to JVM, use `TvmSdk.load()` static method. 
Loaded TVM-SDK is a singleton, you can't use other version of library simultaneously.
Jacki stores wrapped copy of actual TVM-SDK libraries in its resources. To load wrapped library, run:
```java
TvmSdk.load();
```
**Note: We found problems with loading library from resources using Spring's fatJar bundles. Please, use alternative loaders described below if you use fatJar too.**

If you want to use custom binaries or version, you should use other loaders. 
All loaders are just ways to reach library, so you should get/build `ton_client` library first.
You can find TVM Labs [precompiled TVM-SDK files](https://github.com/tvmlabs/tvm-sdk/blob/main/#download-precompiled-binaries) here.
Here are the examples of loader options:
```java
// Loads internal native lib included in your dependency
TvmSdk.load();
// loads library from path saved in environment variable
TvmSdk.load(AbsolutePathLoader.ofSystemEnv("TON_CLIENT_LIB")); 
// loads library from ~ (user home)
TvmSdk.load(AbsolutePathLoader.ofUserDir("libton_client.so")); 
// loads from any absolute path
TvmSdk.load(new AbsolutePathLoader(Path.of("/home/ton/lib/libton_client.so"))); 
// loads library from java.library.path JVM argument
TvmSdk.load(new JavaLibraryPathLoader("ton_client")); 
```

#### Creating config context and specifying endpoints

Context configuration is needed to provide TVM-SDK library with your endpoints, timeouts and other settings.
You can find a list of endpoints here: https://github.com/tvmlabs/.github/tree/main/profile

```java
// creates default TVM-SDK context without specifying endpoints
int contextId1 = TvmSdk.createDefault(); 
// creates default TVM-SDK with specified endpoint
int contextId2 = TvmSdk.createWithEndpoint("http://localhost/graphql"); 
// creates TVM-SDK context from ready JSON string
int contextId4 = TvmSdk.createWithJson(configJsonString);
```

Save your contextId, you will use this id to call TVM-SDK methods.

#### Configuring SDK with Builder

Alternatively, you can call TvmSdk.builder() that provides builder methods for all config values of TVM-SDK.
Thus you can easily configure only needed parts of library.
```java
int contextId3 = TvmSdk.builder()
                       .networkEndpoints("http://localhost/graphql")
                       .networkQueryTimeout(300_000L)
                       .build();
```

### Calling TVM-SDK methods

To call TVM-SDK method, just type <ModuleCapitalCase>.<methodCamelCase>, for example:

```java
// async
Future<String> hashHexString = Boc.getBocHash(contextId, "bocBase64String");
Future<JsonNode> dataJson = Abi.decodeBoc(contextId, types, "bocBase64String", true).data();
// to sync
String hashHexString = TvmSdk.await(Boc.getBocHash(contextId, "bocBase64String"));
JsonNode dataJson = TvmSdk.await(Abi.decodeBoc(contextId, types, "bocBase64String", true)).data();
```

### Adding ABI, TVC & other artifacts

**Jacki** includes easy API to work with files and java resources
(both json-based and byte[]-based). 
Here are simple examples of getting contract ABIs and TVCs artifacts:

* `ContractAbi.ofFile("/path/to/your.abi.json")` - reads abi from file (can be relative)
* `ContractAbi.ofResource("yourresource.abi.json")` - reads abi from resources of your project
* `ContractAbi.ofString("")` -reads abi from JSON string
* `ContractAbi.ofJsonNode(node)` - reads abi from Jackson framework's JsonNode object

* `Tvc.ofFile("/path/to/your.tvc")` - reads tvc from file (can be relative)
* `Tvc.ofResource("yourresource.tvc")` - reads tvc from resources of your project
* `Tvc.ofBase64String("")` -reads tvc from base64 encoded string
* `new Tvc(bytes)` - reads tvc from byte array

Also, you can check `JsonFile`, `JsonResource`, `ByteFile`, `ByteResource` helpers for custom artifacts.

### Crypto

**Jacki** includes basic helpers to
create your seeds, key pairs and signatures. If
you want some specific **TVM-SDK** functions, just use them
firectly as all **TVM-SDK** API is available from **Jacki**.

#### Creating a random keypair

```java
var keys = Credentials.ofRandom(contextId);
String sk = keys.secretKey();
String pk = keys.publicKey();
```

#### Creating a random seed

```java
var seed = Seed.ofRandom(contextId);
String wordsPhrase = seed.phrase();
int wordsCount = seed.words();
```

#### Deriving keys from seed

```java
var keys = Credentials.ofSeed(contextId,seed);
String sk = keys.secretKey();
String pk = keys.publicKey();
```

#### Using existing keys & seeds

```java
var seed = new Seed("<your seed phrase with 12 words or 24 with second constructor param>");
var keys = new Credentials("<publickey string hex>","<secretkey string hex>");
```

#### Using "Signing Box" object

```java
int context = TvmSdk.createDefault();
var keys = Env.RNG_KEYS();
// create implementation of your handle
var boxHandle = TvmSdk.await(Crypto.registerSigningBox(context, new AppSigningBox() {
    @Override
    public String getPublicKey() {
        return "";
    }

    @Override
    public String sign(String unsigned) {
        return "";
    }
})).handle();
// use this handle when creating contract object
var contract = new EverWalletContract(context, 
                                      new Address("0:9400ec4b8629b5293bb6798bbcf3dd25d72e4f114226b5547777d0fc98fe53fa"), 
                                      new Abi.Signer.SigningBox(boxHandle));
// now contract calls will use your signing box
contract.sendTransaction(dest, value, bounce).call();
```

### Smart-contracts

#### Interacting with already deployed contracts

```java
// to use already deployed contract, you should know its ABI and its address
var deployedContractAbi = ContractAbi.ofResource("artifacts/giver/GiverV2.abi.json");
var deployedContractAddress = new Address("0:ece57bcc6c530283becbbd8a3b24d3c5987cdddc3c8b7b33be6e4a6312490415");
// if you don't know contract credentials, use Credentials.NONE
var deployedContractCredentials = new Credentials("2ada2e65ab8eeab09490e3521415f45b6e42df9c760a639bcf53957550b25a16",
                                                  "172af540e43a524763dd53b26a066d472a97c4de37d5498170564510608250c3");
// instantiate your contract
var giverContract = new AbstractContract(contextId,
                                         deployedContractAddress,
                                         deployedContractAbi,
                                         deployedContractCredentials);
// make a call by function name
var functionCallPrepare = giverContract.functionCallBuilder()
        .setFunctionName("getMessages")
        .setFunctionInputs(Map.of()) // provide a map of params
        .setReturnClass(Map.class)
        .build();
// if you didn't provide return class, use callAsMap() and getAsMap() to receive plain JSON
System.out.println(functionCallPrepare.getAsMap().toPrettyString());
```

If you don't want to write function calls by hand, 
use [Contract Generation](#contract-generation) tips below to generate your own Contract classes.

#### Accessing contract account metadata

To access account metadata of certain smart-contract, get Account object from any type of Contract
```java
Account acc = contract.account();
```
Alternatively, you can create Account object from any address
```java
Account acc = Account.ofAddress(contextId, "0:ece57bcc6c530283becbbd8a3b24d3c5987cdddc3c8b7b33be6e4a6312490415");
```
Then, get all needed info from account
```java
acc.boc();
acc.code();
acc.codeHash();
acc.balance();
acc.accType();
```

### Contract Generation

#### Calling generator for your artifacts

`ContractWrapper` class is a generator that will create java wrapper
classes for all your contracts. You need only `abi.json` and `.tvc`
artifacts of your contracts as a source for code generation.

Run the following, specifying your artifacts and where to place
generated classes in params:

```java
ContractWrapper.generate(ContractAbi.ofResource("mycontract.abi.json").abiContract(),
                         Tvc.ofResource("mycontract.tvc"),
                         Path.of("src/gen/java"),
                         "MyContract",
                         "org.example.contract",
                         "org.example.template",
                         new String[]{});
```

Contract and template wrappers will appear in packages that you specified.

If you're working with standard contracts, all wrappers are
already generated (for **Multisig Wallets**, **Givers**, **TIP3** and **TIP4**
contracts and so on).

#### Accessing your newly generated contract wrapper

To access contract account, create instance of your contract class by
passing SDK Provider and address of deployed contract.

```java
MyContract contr = new MyContract(contextId, "0:your_contract_address");
```

#### Accessing generated function wrappers

Now, when your contract object is created, just get handle of one 
of functions by calling one of the contract methods.

```java
FunctionHandle getCustodiansFunctionHandle = contr.getCustodians();
```

Function in this example doesn't have params, but yours can have.

#### Calling Functions in various ways

With `FunctionHandle` you can make external calls, run get methods using remote or local boc and so on like this:

```java
MyContract.ResultOfGetCustodians custodians = getCustodiansFunctionHandle.get();

Map<String,Object> custodiansMap = getCustodiansFunctionHandle.getAsMap();

MyContract.ResultOfGetCustodians custodians = getCustodiansFunctionHandle.call();

Map<String,Object> custodiansMap = getCustodiansFunctionHandle.callAsMap();

MyContract.ResultOfGetCustodians custodians = getCustodiansFunctionHandle.getLocal(locallySavedBoc);
```

All the described functions, handles and return types are auto-generated when you generate contract wrapper

**Variants of calls to function**:

* **get()** - runs getter method and returns auto-generated type
* **call()** - makes external call (make sure you added credentials to contract object if contract checks signatures)
* **getLocal()** - runs getter against provided boc
* **...AsMap()** - each method have AsMap variant that returns Jackson framework's JsonNode object instead of static type result

#### Deploying new contracts

Second class created by contract generator is `MyContractTemplate.class`.
It's a companion class that stores ABI and TVC info for deployment.

### Using wallets and other standard contract wrappers

#### Sending Internal Message from Multisig Wallet

```java
var walletContract = new SafeMultisigWallet(contextId,"", walletKeys);
getCustodiansFunctionHandle.sendFrom(walletContract, CurrencyUnit.VALUE(COIN,"1.25"), true, MessageFlag.FEE_EXTRA);
```
**sendFrom()** method also has **sendFromAsMap()** variant.

Calls and sends also has **...Tree()** variants that can 
be used to monitor transaction tree execution and collect errors.

#### Encoding as Payload

You can encode `FunctionHandle` as a payload for internal call like this:

```java
var payload = getCustodiansFunctionHandle.toPayload();
```

### Deploying smart-contracts

#### Accessing Template

You can create template object with no additional params. 
If you didn't use generator, use `AbstractTemplate` class and 
pass ABI and TVC to it manually.

```java
MyContractTemplate myTemplate = new MyContractTemplate();

var abi = myTemplate.abi(); // getting ABI from template
var tvc = myTemplate.tvc(); // getting TVC from template

myTemplate.tvc().code() // getting code cell
myTemplate.tvc().codeHash() // getting code hash
```

There are much more methods for TVC and ABI, including decoding and encoding 
initial data, various helpers for all sort of interactions.

#### Prepared deployment set

`DeployHandle` is a handle of prepared deployment set with all needed params. 
As with function handles, `Template::prepareDeploy` params may vary depending on your contract -
your static variables and constructor params.

```java
DeployHandle deployHandle = myTemplate.prepareDeploy(contextId, Credentials.NONE,"hello_world");
```

#### Variations of running deploy

```java
// Deploys prepared contract deployment. Will work only when deploy target account address is already paid
// You can check target account address by using deployHandle.toAddress() call
MyContract myContract = deployHandle.deploy();
// deploys prepared contract deployment by using funds from specified wallet
MyContract myContract = deployHandle.deployWithGiver(walletContract, CurrencyUnit.VALUE(COIN,"1.25"));
// deploys prepared contract deployment by using funds from EverNode-SE giver
MyContract myContract = deployHandle.deployWithGiver(EverOSGiver.V2(contextId), CurrencyUnit.VALUE(COIN,"1.25"));
```

Each deployment returns a contract object after deploy is done. 
Also, you can use `deployHandle.toAddress()` if you need only address calculation.

#### Switching Givers

Deployment usually requires giving funds to target address of deployment. 
Here's the example of universal deployment that switches 
between **evernode-se** giver & **msig wallet** without any additional code:

```java
Giver giver = null;

if (isLocalNode()) {
  giver = EverOSGiver.V2(contextId);
} else {
  giver = new SafeMultisigWallet(contextId,"0:your_address");
}

deployHandle.deployWithGiver(giver, CurrencyUnit.VALUE(COIN,"1.25"));
```

This is possible as most wallet classes are implementing Giver interface. 
You can also generate wrappers that implements Giver interface.


#### Offline deployment

Example of creation and signing offline messages for later sending. 

```java
int offlineContext = TvmSdk.builder()
                     .networkSignatureId(1L)
                     .networkQueryTimeout(300_000L)
                     .build();
int i = 0;
var template = new EverWalletTemplate();
// let's generate 5 addresses that match certain condition and send them 1 coin
Predicate<Address> addressCondition = address -> address.makeAddrStd().contains("7777");
while (i < 5) {
    var seed = Env.RNG_SEED(); // creates new seed
    var keys = seed.deriveCredentials(offlineContext); // derives keys from seed
    // let's calculate future TVM Wallet address
    var stateInit = template.getStateInit(offlineContext, keys.publicKey(), BigInteger.ZERO);
    var address = template.getAddress(offlineContext, 0, stateInit);
    
    // check conditions if we want to deploy contract
    if (addressCondition.test(address)) {
        // let's create message bodies offline
        // here we send sendTransaction to ourselves
        var body = new EverWalletContract(offlineContext, address, keys).sendTransaction(address, CurrencyUnit.VALUE(COIN, "1"), false)
                                                                     .toPayload(false);

        System.out.printf("Address: %s%n", address);
        System.out.printf("Seed: %s, public: %s%n", seed.phrase(), keys.publicKey());
        System.out.printf("Message body: %s%n", body);
        System.out.printf("State Init: %s%n", stateInit);

        int onlineContext = TvmSdk.createWithEndpoint("https://gql.venom.foundation/graphql");
        // let's send messages when we're online
        TvmSdk.sendExternalMessage(onlineContext,
                                    address.makeAddrStd(),
                                    EverWalletTemplate.DEFAULT_ABI().ABI(),
                                    stateInit.cellBoc(),
                                    body.cellBoc(),
                                    null);
        i++;
    }
}
```

### Subsriptions

**Unstable** - Subscriptions were heavily changed in the last release, so there can be dragons.

Subscriptions consist of Subscription.Builder class where you describe your 
subscription details. After that, run the subscription with subscribe..() methods. 
Subscription supports specifying multiple GQL filters, multiple consumers, 
saving to queue, manual and auto-unsubscribing on condition.

```java
// let's specify what will consume our event:
Consumer<JsonNode> eventConsumer = jsonNode -> System.out.println(jsonNode.toPrettyString());
// describe our subscription in builder style
var subscriptionBuilder = Subscriptions
        .onAccounts("acc_type", "id")
        .addFilterOnSubscription("id: { eq: \"<your_address>\" }")
        .addFilterOnSubscription("code_hash: { eq: \"<your_hash>\" }")
        .addCallbackConsumer(eventConsumer)
        .setCallbackToQueue(true); // if you don't want to specify consumer, you can switch on adding to internal queue
// let's subsribe
var subscription1 = subscriptionBuilder.subscribeUntilCancel(1);
// let's unsubscribe
subscription1.unsubscribe();
// perhaps some messages were pu in the queue?
int size = subscription1.callbackQueue().size();
// let's reuse builder, but subscribe until first event is fired
var subscription2 = subscriptionBuilder.subscribeUntilFirst(1);
// another one, subscribed until certain condition
var subscription3 = subscriptionBuilder.subscribeUntilCondition(1, jsonNode -> !jsonNode.get("accounts").elements().hasNext());
```

### Currency

All solidity currency constants are available from CurrencyUnit class. 
You can retrieve final bigint about like this

```java
var nanoAmount = CurrencyUnit.VALUE(COIN, "2"); // 2_000_000_000 nanocoins
var nanoAmount2 = CurrencyUnit.VALUE(MILLICOIN, "500.3"); // 500_300_000 nanocoins
```

If your token has custom decimals count, you can specify it like this

```java
var tokenUnit = new CurrencyUnit.CustomToken(12); // my token has 12 decimals
// then use your own tokenunits in all your calls
var nanoValue = CurrencyUnit.VALUE(tokenUnit, "2.2"); // 2_200_000_000_000 nanotokens
```

### Connecting to logging

**Jacki** uses the JDK Platform Loggging (JEP 264: Platform Logging API and Service),
so can be easily bridged to any logging framework. For example, to use log4j2, just add `org.apache.logging.log4j:log4j-jpl` to your Maven/Gradle build.

## Getting Help

If you can't answer in this readme or have a bug/improvement to report:
* Ask in our [Telegram](https://t.me/deplant_chat_en) support chat
* Open a new [Issue](https://github.com/deplant/java4ever-framework/issues/new)
* Read Javadocs: [![javadoc](https://javadoc.io/badge2/tech.deplant.jacki/tvm-api/javadoc.svg)](https://javadoc.io/doc/tech.deplant.jacki/tvm-api)


