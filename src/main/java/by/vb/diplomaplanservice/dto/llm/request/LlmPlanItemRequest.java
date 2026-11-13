package by.vb.diplomaplanservice.dto.llm.request;

import by.vb.diplomaplanservice.entity.enums.DayPeriod;

import java.time.LocalDate;

public record LlmPlanItemRequest(
	LocalDate date,
	DayPeriod period,
	Integer position,
	String name,
	String category,
	Integer estimatedDurationMin
) {
}
