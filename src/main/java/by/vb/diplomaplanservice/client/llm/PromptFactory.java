package by.vb.diplomaplanservice.client.llm;

import by.vb.diplomaplanservice.dto.llm.request.LlmPlanItemRequest;
import by.vb.diplomaplanservice.dto.llm.request.LlmPrompt;
import by.vb.diplomaplanservice.dto.llm.request.LlmReviewRequest;
import by.vb.diplomaplanservice.dto.llm.request.LlmTripRequest;
import by.vb.diplomaplanservice.dto.trip.TripResponse;
import by.vb.diplomaplanservice.entity.PlanVariant;
import by.vb.diplomaplanservice.mapper.llm.LlmPlanItemRequestMapper;
import by.vb.diplomaplanservice.mapper.llm.LlmTripRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromptFactory {
	private static final String PLAN_SYSTEM_PROMPT = """
			You are an experienced travel planner. You create day-by-day sightseeing plans for a trip.

			The trip is given as JSON. budgetAmount is the budget for the whole trip in the given currency.
			categories are the traveller's interests, each with a code, a name and a description.

			Each day is split into three periods:
			- morning: 09:00-12:00
			- afternoon: 12:00-18:00
			- evening: 18:00-23:00

			Rules:
			- Include every date of the trip, from the start date to the end date, exactly once.
			- Only use real places that exist in the destination city or its close surroundings. Use the official name
			  as it appears on Google Maps, so the place can be found by name.
			- Prefer places matching the traveller's categories, and use only the provided category codes.
			- Keep each period realistic: the visit durations plus travel between places must fit into the period.
			- Keep each day geographically compact and list the places of a period in a sensible visiting order.
			- Do not repeat a place within the trip.
			- Respect the budget: avoid expensive attractions if the budget is low.
			- A period may be empty if there is nothing sensible to do.
			""";

	private static final String REVIEW_SYSTEM_PROMPT = """
			You are a strict, impartial reviewer of travel plans. Score the plan against the rubric below.

			The input is JSON with the trip and the plan items. budgetAmount is the budget for the whole trip in the
			given currency. categories are the traveller's interests. Items are listed in visiting order; each has a
			date, a period, its position within the period, the place name, a category code and the estimated visit
			duration in minutes. Missing values are null.
			Every criterion is scored with an integer from 1 to 10.

			preferenceFit - do the activities match the traveller's categories?
			  1: mostly unrelated places, 5: about half match, 10: nearly all match
			variety - is the trip diverse?
			  1: the same kind of place all the time, 5: some repetition, 10: a good mix every day
			pacing - is each day and period realistic?
			  1: exhausting or empty days, 5: some unbalanced periods, 10: balanced with time to rest
			coherence - does each day make geographic and logical sense?
			  1: jumps across the city, 5: some backtracking, 10: every day is compact
			budgetFit - do the activities fit the budget?
			  1: far over budget, 5: borderline, 10: comfortably within budget

			overallScore is your overall judgement of the plan, an integer from 1 to 10.
			summary names the main strengths and weaknesses in at most 400 characters.
			""";

	private final JsonMapper jsonMapper;
	private final LlmTripRequestMapper llmTripRequestMapper;
	private final LlmPlanItemRequestMapper llmPlanItemRequestMapper;

	public LlmPrompt planPrompt(TripResponse trip) {
		LlmTripRequest llmTripRequest = llmTripRequestMapper.mapTo(trip);

		return new LlmPrompt(PLAN_SYSTEM_PROMPT,
				"Create a plan for this trip:\n" + jsonMapper.writeValueAsString(llmTripRequest));
	}

	public LlmPrompt reviewPrompt(TripResponse trip, PlanVariant variant) {
		LlmTripRequest llmTripRequest = llmTripRequestMapper.mapTo(trip);

		List<LlmPlanItemRequest> items =
				variant.getItems().stream().map(llmPlanItemRequestMapper::mapTo).toList();

		LlmReviewRequest input = new LlmReviewRequest(llmTripRequest, items);

		return new LlmPrompt(REVIEW_SYSTEM_PROMPT, "Review this plan:\n" + jsonMapper.writeValueAsString(input));
	}
}
