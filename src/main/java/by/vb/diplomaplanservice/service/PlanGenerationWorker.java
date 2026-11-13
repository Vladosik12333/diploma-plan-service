package by.vb.diplomaplanservice.service;

import by.vb.diplomaplanservice.client.llm.LlmClient;
import by.vb.diplomaplanservice.client.llm.PromptFactory;
import by.vb.diplomaplanservice.dto.llm.response.LlmPlan;
import by.vb.diplomaplanservice.dto.llm.response.LlmReview;
import by.vb.diplomaplanservice.dto.trip.TripResponse;
import by.vb.diplomaplanservice.entity.PlanVariant;
import by.vb.diplomaplanservice.entity.enums.GenerationStatus;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PlanGenerationWorker {
	private final GeneratedPlanService generatedPlanService;
	private final PlanVariantService planVariantService;
	private final PlanReviewService planReviewService;
	private final LlmClient llmClient;
	private final PromptFactory promptFactory;


	@Async
	public void generate(Long planId, TripResponse trip, LlmProvider provider) {
		try {
			generatedPlanService.updateStatus(planId, GenerationStatus.GENERATING);

			LlmPlan llmPlan = llmClient.call(provider, promptFactory.planPrompt(trip),
					LlmPlan.class);

			PlanVariant initial = planVariantService.createInitial(planId, llmPlan);

			generatedPlanService.updateStatus(planId, GenerationStatus.REVIEWING);

			LlmReview llmReview = llmClient.call(provider,
					promptFactory.reviewPrompt(trip, initial), LlmReview.class);

			planReviewService.create(initial, provider, llmReview);

			generatedPlanService.updateStatus(planId, GenerationStatus.DONE);
		} catch (Exception e) {
			log.error("Plan generation failed. planId={}, provider={}", planId, provider, e);
			generatedPlanService.updateStatus(planId, GenerationStatus.FAILED);
		}
	}
}
