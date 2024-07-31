package tech.deplant.jacki.binding;

import com.fasterxml.jackson.databind.JsonNode;

public interface AppObject {
	void consumeParams(int contextId, long appRequestId, JsonNode jsonNode) throws TvmSdkException;
}
