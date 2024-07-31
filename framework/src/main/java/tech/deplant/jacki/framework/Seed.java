package tech.deplant.jacki.framework;

import tech.deplant.jacki.binding.Crypto;
import tech.deplant.jacki.binding.TvmSdk;
import tech.deplant.jacki.binding.TvmSdkException;

/**
 * The type Seed.
 */
public record Seed(String phrase, int words, Crypto.MnemonicDictionary dictionary) {

	/**
	 * Instantiates a new Seed.
	 *
	 * @param phrase the phrase
	 * @param words  the words
	 */
	public Seed(String phrase, int words) {
		this(phrase, words, Crypto.MnemonicDictionary.English);
	}

	/**
	 * The constant DEFAULT_WORD_COUNT.
	 */
	public final static int DEFAULT_WORD_COUNT = 12; //Mnemonic word count
	/**
	 * The constant TON_WORD_COUNT.
	 */
	public final static int TON_WORD_COUNT = 24; //Mnemonic word count

	/**
	 * The constant HD_PATH.
	 */
	public final static String HD_PATH = "m/44'/396'/0'/0/0";

	private static String generateMnemonicPhraseFromRandom(int contextId, int words) throws TvmSdkException {
		return TvmSdk.await(Crypto.mnemonicFromRandom(contextId, Crypto.MnemonicDictionary.English, words)).phrase();
	}

	/**
	 * Generates random 12-words seed phrase
	 *
	 * @param contextId the context id
	 * @return seed
	 * @throws TvmSdkException the ever sdk exception
	 */
	public static Seed ofRandom(int contextId) throws TvmSdkException {
		return new Seed(TvmSdk.await(Crypto.mnemonicFromRandom(contextId, Crypto.MnemonicDictionary.English, DEFAULT_WORD_COUNT)).phrase(), DEFAULT_WORD_COUNT, Crypto.MnemonicDictionary.English);
	}

	/**
	 * Of random ton seed.
	 *
	 * @param contextId the context id
	 * @return the seed
	 * @throws TvmSdkException the ever sdk exception
	 */
	public static Seed ofRandomTon(int contextId) throws TvmSdkException {
		return new Seed(TvmSdk.await(Crypto.mnemonicFromRandom(contextId, Crypto.MnemonicDictionary.Ton, 24)).phrase(), 24, Crypto.MnemonicDictionary.Ton);
	}

	/**
	 * Verify seed phrase boolean.
	 *
	 * @param contextId the context id
	 * @return the boolean
	 * @throws TvmSdkException the ever sdk exception
	 */
	public boolean verifySeedPhrase(int contextId) throws TvmSdkException {
		return TvmSdk.await(Crypto.mnemonicVerify(contextId, phrase(), dictionary(), words())).valid();
	}

	/**
	 * Derive credentials credentials.
	 *
	 * @param contextId the context id
	 * @param path      the path
	 * @return the credentials
	 * @throws TvmSdkException the ever sdk exception
	 */
	public Credentials deriveCredentials(int contextId, String path) throws TvmSdkException {
		Crypto.KeyPair derived = TvmSdk.await(Crypto.mnemonicDeriveSignKeys(contextId, phrase(), path, dictionary(), words()));
		return new Credentials(derived.publicKey(), derived.secretKey());
	}

	/**
	 * Derive credentials credentials.
	 *
	 * @param contextId the context id
	 * @return the credentials
	 * @throws TvmSdkException the ever sdk exception
	 */
	public Credentials deriveCredentials(int contextId) throws TvmSdkException {
		return deriveCredentials(contextId, null);
	}

}
