package by.vb.diplomaplanservice.dto.llm.response;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record LlmPlanItem(
	@JsonPropertyDescription("Official name of a real, existing place, as it appears on Google Maps")
	String name,
	@JsonPropertyDescription("Category code from the provided list")
	String category,
	@JsonPropertyDescription("Estimated visit duration in minutes, excluding travel")
	Integer estimatedDurationMin
) {
}
