package by.vb.diplomaplanservice.dto;

import by.vb.diplomaplanservice.entity.enums.PlanItemIssue;

import java.math.BigDecimal;
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
	BigDecimal latitude,
	BigDecimal longitude,
	String address,
	LocalTime startTime,
	LocalTime endTime,
	Integer estimatedCost,
	LocalTime openTime,
	LocalTime closeTime,
	PlanItemIssue issue
) {
}
