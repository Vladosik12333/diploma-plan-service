package by.vb.diplomaplanservice.dto.llm.request;

import java.util.List;

public record LlmReviewRequest(
	LlmTripRequest trip,
	List<LlmPlanItemRequest> items
) {
}
