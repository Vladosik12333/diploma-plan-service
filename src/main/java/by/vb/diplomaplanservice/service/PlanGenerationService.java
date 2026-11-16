package by.vb.diplomaplanservice.service;

import by.vb.diplomaplanservice.dto.GeneratePlanRequest;
import by.vb.diplomaplanservice.dto.GeneratedPlanSummaryResponse;

public interface PlanGenerationService {
	GeneratedPlanSummaryResponse start(Long userId, GeneratePlanRequest request);
}
