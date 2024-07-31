package tech.deplant.jacki.binding.generator.reference;

public record NoneType(String type) implements ApiType {
	@Override
	public String name() {
		return null;
	}

	@Override
	public String summary() {
		return null;
	}

	@Override
	public String description() {
		return null;
	}
}
