package by.vb.diplomaplanservice.client.llm.impl;

import by.vb.diplomaplanservice.client.llm.LlmClient;
import by.vb.diplomaplanservice.dto.llm.request.LlmPrompt;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LlmClientImpl implements LlmClient {
	private final Map<LlmProvider, ChatClient> chatClients;
	private final Map<LlmProvider, String> chatModelNames;

	@Override
	public <T> T call(LlmProvider provider, LlmPrompt prompt, Class<T> responseType) {
		T response = chatClients.get(provider).prompt()
				.system(prompt.system())
				.user(prompt.user())
				.call()
				.entity(responseType);

		if (response == null) {
			throw new IllegalStateException(provider + " returned an empty response");
		}

		return response;
	}

	@Override
	public String model(LlmProvider provider) {
		return chatModelNames.get(provider);
	}
}
