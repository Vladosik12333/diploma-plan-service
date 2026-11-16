package by.vb.diplomaplanservice.client.llm;

import by.vb.diplomaplanservice.dto.llm.request.LlmPrompt;
import by.vb.diplomaplanservice.dto.trip.TripResponse;
import by.vb.diplomaplanservice.entity.PlanVariant;

public interface PromptFactory {
	LlmPrompt planPrompt(TripResponse trip);

	LlmPrompt reviewPrompt(TripResponse trip, PlanVariant variant);
}
