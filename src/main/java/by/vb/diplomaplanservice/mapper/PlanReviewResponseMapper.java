package by.vb.diplomaplanservice.mapper;

import by.vb.diplomaplanservice.dto.PlanReviewResponse;
import by.vb.diplomaplanservice.entity.PlanReview;
import org.springframework.stereotype.Component;

@Component
public class PlanReviewResponseMapper implements Mapper<PlanReview, PlanReviewResponse> {
	@Override
	public PlanReviewResponse mapTo(PlanReview entity) {
		return new PlanReviewResponse(entity.getProvider(), entity.getModel(), entity.getPreferenceFit(),
				entity.getVariety(), entity.getPacing(), entity.getCoherence(), entity.getBudgetFit(),
				entity.getOverallScore(), entity.getSummary());
	}
}
