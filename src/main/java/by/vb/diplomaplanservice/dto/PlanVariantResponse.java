package by.vb.diplomaplanservice.dto;

import by.vb.diplomaplanservice.entity.enums.PlanVariantType;

import java.util.List;

public record PlanVariantResponse(
	Long id,
	PlanVariantType type,
	List<PlanItemResponse> items,
	PlanReviewResponse review
) {
}
