package by.vb.diplomaplanservice.dto;

import by.vb.diplomaplanservice.entity.enums.LlmProvider;

public record PlanReviewResponse(
	LlmProvider provider,
	String model,
	Integer preferenceFit,
	Integer variety,
	Integer pacing,
	Integer coherence,
	Integer budgetFit,
	Integer overallScore,
	String summary
) {
}
