package by.vb.diplomaplanservice.dto;

import by.vb.diplomaplanservice.entity.enums.GenerationStatus;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;

import java.time.Instant;

public record GeneratedPlanSummaryResponse(
	Long id,
	Long tripId,
	GenerationStatus status,
	LlmProvider provider,
	String model,
	Instant createdAt
) {
}
