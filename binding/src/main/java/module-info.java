module java4ever.binding {
	requires java.compiler;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive deplant.commons;
	requires transitive deplant.javapoet;
	requires com.fasterxml.jackson.datatype.jdk8;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires com.fasterxml.jackson.module.paramnames;
	requires java.net.http;

	opens sdk.linux_x86_64;
	opens sdk.macos_aarch64;
	opens sdk.macos_x86_64;
	opens sdk.win_x86_64;

	exports tech.deplant.jacki.binding;
	exports tech.deplant.jacki.binding.gql;
	exports tech.deplant.jacki.binding.loader;
	exports tech.deplant.jacki.binding.io;
	exports tech.deplant.jacki.binding.generator;
	exports tech.deplant.jacki.binding.generator.reference;
	exports tech.deplant.jacki.binding.generator.jtype;
}