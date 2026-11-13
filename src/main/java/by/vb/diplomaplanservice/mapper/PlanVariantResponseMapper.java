package by.vb.diplomaplanservice.mapper;

import by.vb.diplomaplanservice.dto.PlanVariantResponse;
import by.vb.diplomaplanservice.entity.PlanItem;
import by.vb.diplomaplanservice.entity.PlanVariant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@RequiredArgsConstructor
public class PlanVariantResponseMapper implements Mapper<PlanVariant, PlanVariantResponse> {
	private static final Comparator<PlanItem> ITEM_ORDER = Comparator.comparing(PlanItem::getDayDate)
			.thenComparing(PlanItem::getPeriod)
			.thenComparing(PlanItem::getPosition);

	private final PlanItemResponseMapper planItemResponseMapper;
	private final PlanReviewResponseMapper planReviewResponseMapper;

	@Override
	public PlanVariantResponse mapTo(PlanVariant entity) {
		return new PlanVariantResponse(entity.getId(), entity.getType(),
				entity.getItems().stream().sorted(ITEM_ORDER).map(planItemResponseMapper::mapTo).toList(),
				entity.getReview() == null ? null : planReviewResponseMapper.mapTo(entity.getReview()));
	}
}
