package by.vb.diplomaplanservice.service;

import by.vb.diplomaplanservice.dto.llm.request.LlmPrompt;
import by.vb.diplomaplanservice.entity.LlmJournal;
import by.vb.diplomaplanservice.entity.enums.LlmCallStatus;
import by.vb.diplomaplanservice.entity.enums.LlmCallType;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import org.springframework.ai.chat.model.ChatResponse;

public interface LlmJournalService {
	LlmJournal create(Long planId, LlmCallType type, LlmProvider provider, String model, LlmPrompt prompt,
			ChatResponse response, LlmCallStatus status, long durationMs, Exception error);
}
