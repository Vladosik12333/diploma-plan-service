package by.vb.diplomaplanservice.dto;

import by.vb.diplomaplanservice.entity.enums.DayPeriod;
import by.vb.diplomaplanservice.entity.enums.PlanItemIssue;

import java.time.LocalDate;

public record PlanItemResponse(
	Long id,
	LocalDate dayDate,
	DayPeriod period,
	Integer position,
	String name,
	String googlePlaceId,
	String category,
	Integer estimatedDurationMin,
	PlanItemIssue issue
) {
}
