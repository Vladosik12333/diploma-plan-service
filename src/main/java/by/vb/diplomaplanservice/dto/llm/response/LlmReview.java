package by.vb.diplomaplanservice.dto.llm.response;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record LlmReview(
	@JsonPropertyDescription("Score 1-10: how well the activities match the trip categories")
	Integer preferenceFit,
	@JsonPropertyDescription("Score 1-10: diversity of activities across the trip")
	Integer variety,
	@JsonPropertyDescription("Score 1-10: whether each day and period is realistic and not overloaded")
	Integer pacing,
	@JsonPropertyDescription("Score 1-10: geographic and logical sense of each day")
	Integer coherence,
	@JsonPropertyDescription("Score 1-10: whether the activities fit the trip budget")
	Integer budgetFit,
	@JsonPropertyDescription("Score 1-10: overall judgement of the plan")
	Integer overallScore,
	@JsonPropertyDescription("Main strengths and weaknesses, at most 400 characters")
	String summary
) {
}
