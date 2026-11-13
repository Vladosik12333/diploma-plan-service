package by.vb.diplomaplanservice.mapper;

import by.vb.diplomaplanservice.dto.GeneratedPlanSummaryResponse;
import by.vb.diplomaplanservice.entity.GeneratedPlan;
import org.springframework.stereotype.Component;

@Component
public class GeneratedPlanSummaryResponseMapper implements Mapper<GeneratedPlan, GeneratedPlanSummaryResponse> {
	@Override
	public GeneratedPlanSummaryResponse mapTo(GeneratedPlan entity) {
		return new GeneratedPlanSummaryResponse(entity.getId(), entity.getTripId(), entity.getStatus(),
				entity.getProvider(), entity.getModel(), entity.getCreatedAt());
	}
}
