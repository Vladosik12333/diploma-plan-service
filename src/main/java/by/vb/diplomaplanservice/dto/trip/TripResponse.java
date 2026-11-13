package by.vb.diplomaplanservice.dto.trip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TripResponse(
	Long id,
	String title,
	LocalDate startDate,
	LocalDate endDate,
	BigDecimal budgetAmount,
	String currency,
	TripLocationResponse location,
	List<CategoryResponse> categories
) {
}
