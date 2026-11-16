package by.vb.diplomaplanservice.config;

import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import com.anthropic.models.messages.OutputConfig;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class LlmConfig {
	@Value("${llm.anthropic.effort}") String effort;

	@Bean
	public Map<LlmProvider, ChatClient> chatClients(final AnthropicChatModel anthropicChatModel,
			final OpenAiChatModel openAiChatModel) {
		final ChatClient anthropicChatClient = ChatClient.builder(anthropicChatModel)
				.defaultOptions(AnthropicChatOptions.builder().effort(OutputConfig.Effort.of(effort)))
				.defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
				.build();

		final ChatClient openAiChatClient = ChatClient.builder(openAiChatModel)
				.defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
				.build();

		return Map.of(
				LlmProvider.ANTHROPIC, anthropicChatClient,
				LlmProvider.OPENAI, openAiChatClient);
	}

	@Bean
	public Map<LlmProvider, String> chatModelNames(final AnthropicChatModel anthropicChatModel,
			final OpenAiChatModel openAiChatModel) {
		return Map.of(
				LlmProvider.ANTHROPIC, anthropicChatModel.getOptions().getModel(),
				LlmProvider.OPENAI, openAiChatModel.getOptions().getModel());
	}
}
