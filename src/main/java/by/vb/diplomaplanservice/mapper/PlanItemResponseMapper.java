package by.vb.diplomaplanservice.mapper;

import by.vb.diplomaplanservice.dto.PlanItemResponse;
import by.vb.diplomaplanservice.entity.PlanItem;
import org.springframework.stereotype.Component;

@Component
public class PlanItemResponseMapper implements Mapper<PlanItem, PlanItemResponse> {
	@Override
	public PlanItemResponse mapTo(final PlanItem entity) {
		return new PlanItemResponse(entity.getId(), entity.getDayDate(), entity.getPosition(), entity.getName(),
				entity.getGooglePlaceId(), entity.getCategory(), entity.getEstimatedDurationMin(),
				entity.getImportance(), entity.getStartTime(), entity.getEndTime(), entity.getEstimatedCost(),
				entity.getIssue());
	}
}
