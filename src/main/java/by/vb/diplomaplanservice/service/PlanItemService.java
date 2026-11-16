package by.vb.diplomaplanservice.service;

import by.vb.diplomaplanservice.dto.llm.response.LlmPlan;
import by.vb.diplomaplanservice.entity.PlanItem;
import by.vb.diplomaplanservice.entity.PlanVariant;

import java.util.List;

public interface PlanItemService {
	List<PlanItem> createItems(PlanVariant variant, LlmPlan plan);
}
