package by.vb.diplomaplanservice.service;

import by.vb.diplomaplanservice.dto.llm.response.LlmReview;
import by.vb.diplomaplanservice.entity.PlanReview;
import by.vb.diplomaplanservice.entity.PlanVariant;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;

public interface PlanReviewService {
	PlanReview create(PlanVariant variant, LlmProvider reviewer, LlmReview llmReview);
}
