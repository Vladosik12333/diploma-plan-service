package by.vb.diplomaplanservice.dto.llm.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.time.LocalTime;

public record LlmPlanItem(
	@JsonPropertyDescription("Official name of a real, existing place, as it appears on Google Maps")
	String name,
	@JsonPropertyDescription("Category code from the provided list")
	String category,
	@JsonPropertyDescription("Estimated visit duration in minutes, excluding travel")
	Integer estimatedDurationMin,
	@JsonPropertyDescription("How important the place is for this traveller, from 1 (optional) to 5 (must see)")
	Integer importance,
	@JsonPropertyDescription("Planned arrival time in format HH:mm")
	@JsonFormat(pattern = TIME_PATTERN)
	LocalTime startTime,
	@JsonPropertyDescription("Planned departure time in format HH:mm")
	@JsonFormat(pattern = TIME_PATTERN)
	LocalTime endTime,
	@JsonPropertyDescription("Estimated cost per person in the trip currency, rounded to a whole number, 0 if free")
	Integer estimatedCost
) {
	private static final String TIME_PATTERN = "HH:mm[:ss][XXX]";
}
