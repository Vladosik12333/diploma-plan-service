package by.vb.diplomaplanservice.service;

import by.vb.diplomaplanservice.dto.llm.response.LlmPlan;
import by.vb.diplomaplanservice.entity.PlanVariant;
import by.vb.diplomaplanservice.entity.enums.PlanVariantType;
import by.vb.diplomaplanservice.repository.GeneratedPlanRepository;
import by.vb.diplomaplanservice.repository.PlanVariantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PlanVariantService {
	private final PlanVariantRepository planVariantRepository;
	private final PlanItemService planItemService;
	private final GeneratedPlanRepository generatedPlanRepository;

	@Transactional
	public PlanVariant createInitial(Long planId, LlmPlan llmPlan) {
		if (llmPlan.days() == null || llmPlan.days().isEmpty()) {
			throw new IllegalStateException("Generated plan has no days");
		}

		PlanVariant variant = new PlanVariant();

		variant.setGeneratedPlan(generatedPlanRepository.getReferenceById(planId));
		variant.setType(PlanVariantType.INITIAL);

		PlanVariant saved = planVariantRepository.save(variant);

		planItemService.createItems(saved, llmPlan);

		log.info("Saved INITIAL variant. planId={}, items={}", planId, saved.getItems().size());

		return saved;
	}
}
