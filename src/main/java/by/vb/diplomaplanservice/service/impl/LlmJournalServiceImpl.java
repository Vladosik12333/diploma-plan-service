package by.vb.diplomaplanservice.service.impl;

import by.vb.diplomaplanservice.constant.PlanConstants;
import by.vb.diplomaplanservice.dto.llm.request.LlmPrompt;
import by.vb.diplomaplanservice.entity.LlmJournal;
import by.vb.diplomaplanservice.entity.enums.LlmCallStatus;
import by.vb.diplomaplanservice.entity.enums.LlmCallType;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import by.vb.diplomaplanservice.repository.LlmJournalRepository;
import by.vb.diplomaplanservice.service.LlmJournalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LlmJournalServiceImpl implements LlmJournalService {
	private final LlmJournalRepository llmJournalRepository;

	@Override
	@Transactional
	public LlmJournal create(final Long planId, final LlmCallType type, final LlmProvider provider,
			final String model, final LlmPrompt prompt, final ChatResponse response, final LlmCallStatus status,
			final long durationMs, final Exception error) {
		final LlmJournal journal = new LlmJournal();

		journal.setGeneratedPlanId(planId);
		journal.setType(type);
		journal.setProvider(provider);
		journal.setModel(model);
		journal.setStatus(status);
		journal.setDurationMs(durationMs);
		journal.setSystemPrompt(prompt.system());
		journal.setUserPrompt(prompt.user());

		if (response != null) {
			final Usage usage = response.getMetadata().getUsage();
			final Generation result = response.getResult();

			if (usage != null) {
				journal.setInputTokens(usage.getPromptTokens());
				journal.setOutputTokens(usage.getCompletionTokens());
			}

			if (result != null) {
				journal.setResponse(result.getOutput().getText());
			}
		}

		if (error != null) {
			journal.setErrorMessage(StringUtils.truncate(error.getClass().getSimpleName() + ": " + error.getMessage(),
					PlanConstants.ERROR_MESSAGE_MAX_LENGTH));
		}

		final LlmJournal saved = llmJournalRepository.save(journal);

		log.info("Saved LLM journal entry. planId={}, type={}, provider={}, status={}, durationMs={}", planId, type,
				provider, status, durationMs);

		return saved;
	}
}
