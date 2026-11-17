package by.vb.diplomaplanservice.client.llm.impl;

import by.vb.diplomaplanservice.client.llm.LlmClient;
import by.vb.diplomaplanservice.dto.llm.request.LlmPrompt;
import by.vb.diplomaplanservice.entity.enums.LlmCallStatus;
import by.vb.diplomaplanservice.entity.enums.LlmCallType;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import by.vb.diplomaplanservice.exception.LlmException;
import by.vb.diplomaplanservice.service.LlmJournalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LlmClientImpl implements LlmClient {
	private final Map<LlmProvider, ChatClient> chatClients;
	private final Map<LlmProvider, String> chatModelNames;
	private final LlmJournalService llmJournalService;

	@Override
	public <T> T call(final Long planId, final LlmCallType type, final LlmProvider provider, final LlmPrompt prompt,
			final Class<T> responseType) {
		final long startedAt = System.currentTimeMillis();

		ChatResponse chatResponse = null;
		LlmCallStatus status = LlmCallStatus.FAILED;
		Exception error = null;

		try {
			final ResponseEntity<ChatResponse, T> response = chatClients.get(provider).prompt()
					.system(prompt.system())
					.user(prompt.user())
					.call()
					.responseEntity(responseType);

			chatResponse = response.response();

			if (response.entity() == null) {
				throw new LlmException(provider, type, provider + " returned an empty " + type + " response");
			}

			status = LlmCallStatus.SUCCESS;

			return response.entity();
		} catch (final LlmException e) {
			error = e;
			throw e;
		} catch (final RuntimeException e) {
			error = e;
			throw new LlmException(provider, type, provider + " " + type + " call failed", e);
		} finally {
			journal(planId, type, provider, prompt, chatResponse, status, System.currentTimeMillis() - startedAt,
					error);
		}
	}

	@Override
	public String model(final LlmProvider provider) {
		return chatModelNames.get(provider);
	}

	private void journal(final Long planId, final LlmCallType type, final LlmProvider provider,
			final LlmPrompt prompt, final ChatResponse response, final LlmCallStatus status, final long durationMs,
			final Exception error) {
		try {
			llmJournalService.create(planId, type, provider, model(provider), prompt, response, status, durationMs,
					error);
		} catch (final RuntimeException e) {
			log.warn("Failed to save LLM journal entry. planId={}, type={}, provider={}", planId, type, provider, e);
		}
	}
}
