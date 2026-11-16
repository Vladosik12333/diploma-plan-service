package by.vb.diplomaplanservice.dto.llm.response;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.math.BigDecimal;
import java.time.LocalTime;

public record LlmPlanItem(
	@JsonPropertyDescription("Official name of a real, existing place, as it appears on Google Maps")
	String name,
	@JsonPropertyDescription("Category code from the provided list")
	String category,
	@JsonPropertyDescription("Estimated visit duration in minutes, excluding travel")
	Integer estimatedDurationMin,
	@JsonPropertyDescription("Latitude of the place in decimal degrees")
	BigDecimal latitude,
	@JsonPropertyDescription("Longitude of the place in decimal degrees")
	BigDecimal longitude,
	@JsonPropertyDescription("Street address of the place")
	String address,
	@JsonPropertyDescription("Planned arrival time in format HH:mm")
	LocalTime startTime,
	@JsonPropertyDescription("Planned departure time in format HH:mm")
	LocalTime endTime,
	@JsonPropertyDescription("Estimated cost per person in the trip currency, rounded to a whole number, 0 if free")
	Integer estimatedCost,
	@JsonPropertyDescription("Opening time on this date in format HH:mm, null if always open")
	LocalTime openTime,
	@JsonPropertyDescription("Closing time on this date in format HH:mm, null if always open")
	LocalTime closeTime
) {
}
