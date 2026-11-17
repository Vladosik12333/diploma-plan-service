package by.vb.diplomaplanservice.dto;

import by.vb.diplomaplanservice.entity.enums.PlanItemIssue;

import java.time.LocalDate;
import java.time.LocalTime;

public record PlanItemResponse(
	Long id,
	LocalDate dayDate,
	Integer position,
	String name,
	String googlePlaceId,
	String category,
	Integer estimatedDurationMin,
	Integer importance,
	LocalTime startTime,
	LocalTime endTime,
	Integer estimatedCost,
	PlanItemIssue issue
) {
}
