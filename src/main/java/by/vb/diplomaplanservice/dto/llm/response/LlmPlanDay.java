package by.vb.diplomaplanservice.dto.llm.response;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.time.LocalDate;
import java.util.List;

public record LlmPlanDay(
	@JsonPropertyDescription("Date in ISO format yyyy-MM-dd")
	LocalDate date,
	@JsonPropertyDescription("Activities of the day, in visiting order")
	List<LlmPlanItem> items
) {
}
