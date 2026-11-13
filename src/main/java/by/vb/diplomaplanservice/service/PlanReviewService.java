package by.vb.diplomaplanservice.service;

import by.vb.diplomaplanservice.client.llm.LlmClient;
import by.vb.diplomaplanservice.constant.PlanConstants;
import by.vb.diplomaplanservice.dto.llm.response.LlmReview;
import by.vb.diplomaplanservice.entity.PlanReview;
import by.vb.diplomaplanservice.entity.PlanVariant;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import by.vb.diplomaplanservice.repository.PlanReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PlanReviewService {
	private final PlanReviewRepository planReviewRepository;
	private final LlmClient llmClient;

	@Transactional
	public PlanReview create(PlanVariant variant, LlmProvider reviewer, LlmReview llmReview) {
		PlanReview review = new PlanReview();

		review.setPlanVariant(variant);
		review.setProvider(reviewer);
		review.setModel(llmClient.model(reviewer));

		review.setPreferenceFit(toScore(llmReview.preferenceFit()));
		review.setVariety(toScore(llmReview.variety()));
		review.setPacing(toScore(llmReview.pacing()));
		review.setCoherence(toScore(llmReview.coherence()));
		review.setBudgetFit(toScore(llmReview.budgetFit()));
		review.setOverallScore(toScore(llmReview.overallScore()));

		review.setSummary(
				StringUtils.truncate(llmReview.summary(), PlanConstants.SUMMARY_MAX_LENGTH));

		PlanReview saved = planReviewRepository.save(review);

		log.info("Saved review. planVariantId={}, overallScore={}", variant.getId(),
				saved.getOverallScore());

		return saved;
	}

	private Integer toScore(Integer score) {
		if (score == null || score < PlanConstants.MIN_SCORE || score > PlanConstants.MAX_SCORE) {
			return null;
		}

		return score;
	}
}
