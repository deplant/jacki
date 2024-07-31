package tech.deplant.jacki.gen;

import  tech.deplant.jacki.binding.generator.ParseGql;
import tech.deplant.jacki.binding.generator.reference.ApiReference;

import java.io.IOException;

public class GqlGenerator {
	public static void main(String[] args) {
		ApiReference apiReference = null;
		try {
			//ParseGql.updateGqlSchemaInfo(ParseGql.getSchemaFromGraphQL("codegen/generator-config.json"));
			ParseGql.generateFromSchema("gql.json");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


}
