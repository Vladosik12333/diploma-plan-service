package by.vb.diplomaplanservice.mapper.llm;

import by.vb.diplomaplanservice.dto.llm.request.LlmPlanItemRequest;
import by.vb.diplomaplanservice.entity.PlanItem;
import by.vb.diplomaplanservice.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class LlmPlanItemRequestMapper implements Mapper<PlanItem, LlmPlanItemRequest> {
	@Override
	public LlmPlanItemRequest mapTo(PlanItem source) {
		return new LlmPlanItemRequest(source.getDayDate(), source.getPeriod(), source.getPosition(), source.getName(),
				source.getCategory(), source.getEstimatedDurationMin());
	}
}
