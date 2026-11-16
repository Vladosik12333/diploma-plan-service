package by.vb.diplomaplanservice.dto.llm.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record LlmPlanItemRequest(
	LocalDate date,
	Integer position,
	String name,
	String category,
	Integer estimatedDurationMin,
	BigDecimal latitude,
	BigDecimal longitude,
	String address,
	LocalTime startTime,
	LocalTime endTime,
	Integer estimatedCost,
	LocalTime openTime,
	LocalTime closeTime
) {
}
