package tech.deplant.jacki.framework.datatype;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The type Sol string.
 */
public record SolString(String value) implements AbiValue<String> {

	@Override
	public String toString() {
		return value();
	}

	@Override
	public String toJava() {
		return toString();
	}

	@JsonValue
	public String toABI() {
		return value();
	}

	@Override
	public AbiType type() {
		return new AbiType(AbiTypePrefix.STRING,0,false);
	}
}
