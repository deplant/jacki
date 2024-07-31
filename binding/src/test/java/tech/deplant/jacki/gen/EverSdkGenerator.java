package tech.deplant.jacki.gen;

import tech.deplant.jacki.binding.generator.ParserEngine;
import tech.deplant.jacki.binding.generator.reference.ApiReference;

import java.io.IOException;

public class EverSdkGenerator {
	public static void main(String[] args) {
		ApiReference apiReference = null;
		try {
			apiReference = ParserEngine.ofJsonResource("api.json");
			ParserEngine.parse(apiReference);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}