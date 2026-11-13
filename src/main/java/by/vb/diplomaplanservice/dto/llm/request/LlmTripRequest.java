package by.vb.diplomaplanservice.dto.llm.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record LlmTripRequest(
	String title,
	String city,
	String country,
	LocalDate startDate,
	LocalDate endDate,
	BigDecimal budgetAmount,
	String currency,
	List<LlmCategoryRequest> categories
) {
}
