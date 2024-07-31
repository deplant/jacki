package tech.deplant.jacki.frtest.codegen;

import tech.deplant.jacki.binding.TvmSdkException;
import tech.deplant.jacki.framework.generator.ContractWrapper;

import java.io.IOException;

public class ContractGenerator {
	public static void main(String[] args) {
		try {
			ContractWrapper.generateFromConfig("codegen/generator-config.json");
		} catch (IOException | TvmSdkException e) {
			throw new RuntimeException(e);
		}
	}

}
