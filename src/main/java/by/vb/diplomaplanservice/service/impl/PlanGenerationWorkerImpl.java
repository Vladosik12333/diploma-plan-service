package by.vb.diplomaplanservice.service.impl;

import by.vb.diplomaplanservice.client.llm.LlmClient;
import by.vb.diplomaplanservice.client.llm.PromptFactory;
import by.vb.diplomaplanservice.dto.llm.response.LlmPlan;
import by.vb.diplomaplanservice.dto.llm.response.LlmReview;
import by.vb.diplomaplanservice.dto.trip.TripResponse;
import by.vb.diplomaplanservice.entity.PlanVariant;
import by.vb.diplomaplanservice.entity.enums.GenerationStatus;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import by.vb.diplomaplanservice.service.GeneratedPlanService;
import by.vb.diplomaplanservice.service.PlanGenerationWorker;
import by.vb.diplomaplanservice.service.PlanReviewService;
import by.vb.diplomaplanservice.service.PlanVariantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PlanGenerationWorkerImpl implements PlanGenerationWorker {
	private final GeneratedPlanService generatedPlanService;
	private final PlanVariantService planVariantService;
	private final PlanReviewService planReviewService;
	private final LlmClient llmClient;
	private final PromptFactory promptFactory;


	@Override
	@Async
	public void generate(final Long planId, final TripResponse trip, final LlmProvider provider) {
		try {
			generatedPlanService.updateStatus(planId, GenerationStatus.GENERATING);

			final LlmPlan llmPlan = llmClient.call(provider, promptFactory.planPrompt(trip),
					LlmPlan.class);

			final PlanVariant initial = planVariantService.createInitial(planId, llmPlan);

			generatedPlanService.updateStatus(planId, GenerationStatus.REVIEWING);

			final LlmReview llmReview = llmClient.call(provider,
					promptFactory.reviewPrompt(trip, initial), LlmReview.class);

			planReviewService.create(initial, provider, llmReview);

			generatedPlanService.updateStatus(planId, GenerationStatus.DONE);
		} catch (final Exception e) {
			log.error("Plan generation failed. planId={}, provider={}", planId, provider, e);
			generatedPlanService.updateStatus(planId, GenerationStatus.FAILED);
		}
	}
}
