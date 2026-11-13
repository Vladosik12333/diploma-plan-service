package by.vb.diplomaplanservice.client.llm;

import by.vb.diplomaplanservice.dto.llm.request.LlmPrompt;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LlmClient {
	private final ChatClient anthropicChatClient;
	private final ChatClient openAiChatClient;
	private final AnthropicChatModel anthropicChatModel;
	private final OpenAiChatModel openAiChatModel;

	public <T> T call(LlmProvider provider, LlmPrompt prompt, Class<T> responseType) {
		ChatClient chatClient = switch (provider) {
			case ANTHROPIC -> anthropicChatClient;
			case OPENAI -> openAiChatClient;
		};

		T response = chatClient.prompt()
				.system(prompt.system())
				.user(prompt.user())
				.call()
				.entity(responseType);

		if (response == null) {
			throw new IllegalStateException(provider + " returned an empty response");
		}

		return response;
	}

	public String model(LlmProvider provider) {
		return switch (provider) {
			case ANTHROPIC -> anthropicChatModel.getOptions().getModel();
			case OPENAI -> openAiChatModel.getOptions().getModel();
		};
	}
}
