package by.vb.diplomaplanservice.service;

import by.vb.diplomaplanservice.dto.GeneratePlanRequest;
import by.vb.diplomaplanservice.dto.GeneratedPlanResponse;
import by.vb.diplomaplanservice.dto.GeneratedPlanSummaryResponse;
import by.vb.diplomaplanservice.entity.enums.GenerationStatus;

import java.util.List;

public interface GeneratedPlanService {
	GeneratedPlanSummaryResponse create(Long userId, GeneratePlanRequest request);

	void updateStatus(Long planId, GenerationStatus status);

	List<GeneratedPlanSummaryResponse> getTripPlans(Long tripId, Long userId);

	GeneratedPlanResponse getPlan(Long planId, Long userId);
}
