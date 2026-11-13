package by.vb.diplomaplanservice.mapper;

import by.vb.diplomaplanservice.dto.GeneratedPlanResponse;
import by.vb.diplomaplanservice.entity.GeneratedPlan;
import by.vb.diplomaplanservice.entity.enums.PlanVariantType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeneratedPlanResponseMapper implements Mapper<GeneratedPlan, GeneratedPlanResponse> {
	private final PlanVariantResponseMapper planVariantResponseMapper;

	@Override
	public GeneratedPlanResponse mapTo(GeneratedPlan entity) {
		return new GeneratedPlanResponse(entity.getId(), entity.getTripId(), entity.getStatus(),
				entity.getProvider(), entity.getModel(),
				entity.findVariant(PlanVariantType.INITIAL).map(planVariantResponseMapper::mapTo).orElse(null),
				entity.findVariant(PlanVariantType.OPTIMIZED).map(planVariantResponseMapper::mapTo).orElse(null),
				entity.getCreatedAt());
	}
}
