package by.vb.diplomaplanservice.dto.llm.response;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.time.LocalDate;
import java.util.List;

public record LlmPlanDay(
	@JsonPropertyDescription("Date in ISO format yyyy-MM-dd")
	LocalDate date,
	@JsonPropertyDescription("Activities between 09:00 and 12:00, in visiting order")
	List<LlmPlanItem> morning,
	@JsonPropertyDescription("Activities between 12:00 and 18:00, in visiting order")
	List<LlmPlanItem> afternoon,
	@JsonPropertyDescription("Activities between 18:00 and 23:00, in visiting order")
	List<LlmPlanItem> evening
) {
}
