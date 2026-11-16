package by.vb.diplomaplanservice.service;

import by.vb.diplomaplanservice.dto.trip.TripResponse;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;

public interface PlanGenerationWorker {
	void generate(Long planId, TripResponse trip, LlmProvider provider);
}
