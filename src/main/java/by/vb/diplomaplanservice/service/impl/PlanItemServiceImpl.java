package by.vb.diplomaplanservice.service.impl;

import by.vb.diplomaplanservice.constant.PlanConstants;
import by.vb.diplomaplanservice.dto.llm.response.LlmPlan;
import by.vb.diplomaplanservice.dto.llm.response.LlmPlanDay;
import by.vb.diplomaplanservice.dto.llm.response.LlmPlanItem;
import by.vb.diplomaplanservice.entity.PlanItem;
import by.vb.diplomaplanservice.entity.PlanVariant;
import by.vb.diplomaplanservice.repository.PlanItemRepository;
import by.vb.diplomaplanservice.service.PlanItemService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanItemServiceImpl implements PlanItemService {
	private static final BigDecimal MAX_LATITUDE = BigDecimal.valueOf(90);
	private static final BigDecimal MAX_LONGITUDE = BigDecimal.valueOf(180);

	private final PlanItemRepository planItemRepository;

	@Override
	@Transactional
	public List<PlanItem> createItems(final PlanVariant variant, final LlmPlan plan) {
		final List<PlanItem> items = Stream.ofNullable(plan.days())
				.flatMap(List::stream)
				.filter(Objects::nonNull)
				.filter(day -> Objects.nonNull(day.date()))
				.map(this::createDayItems)
				.flatMap(List::stream)
				.toList();

		items.forEach(variant::addItem);

		return planItemRepository.saveAll(items);
	}

	private List<PlanItem> createDayItems(final LlmPlanDay llmPlanDay) {
		final LocalDate date = llmPlanDay.date();
		final List<LlmPlanItem> llmItems = llmPlanDay.items();

		final List<PlanItem> items = new ArrayList<>();

		if (Objects.isNull(llmItems)) {
			return items;
		}

		for (final LlmPlanItem llmItem : llmItems) {
			if (llmItem != null && StringUtils.isNotBlank(llmItem.name())) {
				final PlanItem item = new PlanItem();

				item.setDayDate(date);
				item.setPosition(items.size() + 1);
				item.setName(StringUtils.truncate(llmItem.name(), PlanConstants.ITEM_NAME_MAX_LENGTH));
				item.setCategory(StringUtils.truncate(llmItem.category(), PlanConstants.CATEGORY_MAX_LENGTH));
				item.setEstimatedDurationMin(llmItem.estimatedDurationMin());
				item.setLatitude(toCoordinate(llmItem.latitude(), MAX_LATITUDE));
				item.setLongitude(toCoordinate(llmItem.longitude(), MAX_LONGITUDE));
				item.setAddress(StringUtils.truncate(llmItem.address(), PlanConstants.ADDRESS_MAX_LENGTH));
				item.setStartTime(llmItem.startTime());
				item.setEndTime(llmItem.endTime());
				item.setEstimatedCost(toCost(llmItem.estimatedCost()));
				item.setOpenTime(llmItem.openTime());
				item.setCloseTime(llmItem.closeTime());

				items.add(item);
			}
		}

		return items;
	}

	private BigDecimal toCoordinate(final BigDecimal value, final BigDecimal max) {
		if (value == null || value.abs().compareTo(max) > 0) {
			return null;
		}

		return value.setScale(PlanConstants.COORDINATE_SCALE, RoundingMode.HALF_UP);
	}

	private Integer toCost(final Integer value) {
		if (value == null || value < 0) {
			return null;
		}

		return value;
	}
}
