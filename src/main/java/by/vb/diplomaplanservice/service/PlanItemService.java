package by.vb.diplomaplanservice.service;

import by.vb.diplomaplanservice.constant.PlanConstants;
import by.vb.diplomaplanservice.dto.llm.response.LlmPlan;
import by.vb.diplomaplanservice.dto.llm.response.LlmPlanItem;
import by.vb.diplomaplanservice.entity.PlanItem;
import by.vb.diplomaplanservice.entity.PlanVariant;
import by.vb.diplomaplanservice.entity.enums.DayPeriod;
import by.vb.diplomaplanservice.repository.PlanItemRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanItemService {
	private final PlanItemRepository planItemRepository;

	@Transactional
	public List<PlanItem> createItems(PlanVariant variant, LlmPlan plan) {
		List<PlanItem> items = Stream.ofNullable(plan.days())
				.flatMap(List::stream)
				.filter(Objects::nonNull)
				.filter(day -> Objects.nonNull(day.date()))
				.flatMap(day -> Stream.of(
						createPeriodItems(day.date(), DayPeriod.MORNING, day.morning()),
						createPeriodItems(day.date(), DayPeriod.AFTERNOON, day.afternoon()),
						createPeriodItems(day.date(), DayPeriod.EVENING, day.evening())))
				.flatMap(List::stream)
				.toList();

		items.forEach(variant::addItem);

		return planItemRepository.saveAll(items);
	}

	private List<PlanItem> createPeriodItems(LocalDate date, DayPeriod period, List<LlmPlanItem> llmItems) {
		List<PlanItem> items = new ArrayList<>();

		if (Objects.isNull(llmItems)) {
			return items;
		}

		for (LlmPlanItem llmItem : llmItems) {
			if (llmItem != null && StringUtils.isNotBlank(llmItem.name())) {
				PlanItem item = new PlanItem();

				item.setDayDate(date);
				item.setPeriod(period);
				item.setPosition(items.size() + 1);
				item.setName(StringUtils.truncate(llmItem.name(), PlanConstants.ITEM_NAME_MAX_LENGTH));
				item.setCategory(StringUtils.truncate(llmItem.category(), PlanConstants.CATEGORY_MAX_LENGTH));
				item.setEstimatedDurationMin(llmItem.estimatedDurationMin());

				items.add(item);
			}
		}

		return items;
	}
}
