package by.vb.diplomaplanservice.mapper.llm;

import by.vb.diplomaplanservice.dto.llm.request.LlmCategoryRequest;
import by.vb.diplomaplanservice.dto.llm.request.LlmTripRequest;
import by.vb.diplomaplanservice.dto.trip.TripResponse;
import by.vb.diplomaplanservice.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class LlmTripRequestMapper implements Mapper<TripResponse, LlmTripRequest> {
	@Override
	public LlmTripRequest mapTo(TripResponse source) {
		return new LlmTripRequest(source.title(), source.location().city(), source.location().country(),
				source.startDate(), source.endDate(), source.budgetAmount(), source.currency(),
				source.categories().stream()
						.map(category -> new LlmCategoryRequest(category.code(), category.name(),
								category.description()))
						.toList());
	}
}
