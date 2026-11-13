package by.vb.diplomaplanservice.config;

import com.anthropic.models.messages.OutputConfig;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LlmConfig {
	@Value("${llm.anthropic.effort}") String effort;

	@Bean
	public ChatClient anthropicChatClient(AnthropicChatModel anthropicChatModel) {
		return ChatClient.builder(anthropicChatModel)
				.defaultOptions(AnthropicChatOptions.builder().effort(OutputConfig.Effort.of(effort)))
				.defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
				.build();
	}

	@Bean
	public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel) {
		return ChatClient.builder(openAiChatModel)
				.defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
				.build();
	}
}
