package by.vb.diplomaplanservice.client.llm;

import by.vb.diplomaplanservice.entity.enums.LlmCallType;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import org.springframework.ai.chat.prompt.Prompt;

public interface LlmClient {
	<T> T call(Long planId, LlmCallType type, LlmProvider provider, Prompt prompt, Class<T> responseType);

	String model(LlmProvider provider);
}
