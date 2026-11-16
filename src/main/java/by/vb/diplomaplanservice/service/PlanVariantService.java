package by.vb.diplomaplanservice.service;

import by.vb.diplomaplanservice.dto.llm.response.LlmPlan;
import by.vb.diplomaplanservice.entity.PlanVariant;

public interface PlanVariantService {
	PlanVariant createInitial(Long planId, LlmPlan llmPlan);
}
