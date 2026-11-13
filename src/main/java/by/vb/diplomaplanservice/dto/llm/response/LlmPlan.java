package by.vb.diplomaplanservice.dto.llm.response;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.List;

public record LlmPlan(
	@JsonPropertyDescription("One entry per trip date, in chronological order")
	List<LlmPlanDay> days
) {
}
