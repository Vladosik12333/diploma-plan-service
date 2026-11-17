package by.vb.diplomaplanservice.client.llm;

import by.vb.diplomaplanservice.dto.trip.TripResponse;
import org.springframework.ai.chat.prompt.Prompt;

public interface PromptFactory {
	Prompt planPrompt(TripResponse trip);
}
