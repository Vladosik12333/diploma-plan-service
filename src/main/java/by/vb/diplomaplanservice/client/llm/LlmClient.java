package by.vb.diplomaplanservice.client.llm;

import by.vb.diplomaplanservice.dto.llm.request.LlmPrompt;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;

public interface LlmClient {
	<T> T call(LlmProvider provider, LlmPrompt prompt, Class<T> responseType);

	String model(LlmProvider provider);
}
