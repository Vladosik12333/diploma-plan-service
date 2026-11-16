package by.vb.diplomaplanservice.client.llm.impl;

import by.vb.diplomaplanservice.client.llm.PromptFactory;
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
public class PromptFactoryImpl implements PromptFactory {
	private static final String PLAN_SYSTEM_PROMPT = """
			You are an experienced travel planner. You create day-by-day sightseeing plans for a trip.

			The trip is given as JSON. budgetAmount is the budget for the whole trip in the given currency.
			categories are the traveller's interests, each with a code, a name and a description.

			Activities take place between 09:00 and 23:00.

			Rules:
			- Include every date of the trip, from the start date to the end date, exactly once.
			- Only use real places that exist in the destination city or its close surroundings. Use the official name
			  as it appears on Google Maps, so the place can be found by name.
			- Prefer places matching the traveller's categories, and use only the provided category codes.
			- Keep each day realistic: the visit durations plus travel between places must fit into the day, with
			  time for meals and rest.
			- Keep each day geographically compact and list its places in visiting order.
			- Do not repeat a place within the trip.
			- Respect the budget: avoid expensive attractions if the budget is low.
			- Give the real coordinates and street address of every place.
			- Give each place a start and end time. The end time is the start time plus the visit duration; leave
			  a realistic gap for travel before the next place.
			- Schedule each visit within the place's opening hours on that date.
			- Estimate the cost per person in the trip currency as a whole number, such as an entrance ticket or a
			  typical meal. Use 0 for free places.
			""";

	private static final String REVIEW_SYSTEM_PROMPT = """
			You are a strict, impartial reviewer of travel plans. Score the plan against the rubric below.

			The input is JSON with the trip and the plan items. budgetAmount is the budget for the whole trip in the
			given currency. categories are the traveller's interests. Items are listed in visiting order; each has a
			date, its position within the day, the place name, a category code, the estimated visit duration in
			minutes, the coordinates and address, the planned start and end time, the estimated cost per person and
			the opening hours on that date. Missing values are null.
			Every criterion is scored with an integer from 1 to 10.

			preferenceFit - do the activities match the traveller's categories?
			  1: mostly unrelated places, 5: about half match, 10: nearly all match
			variety - is the trip diverse?
			  1: the same kind of place all the time, 5: some repetition, 10: a good mix every day
			pacing - is each day realistic?
			  1: exhausting or empty days, 5: some unbalanced days, 10: balanced with time to rest
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

	@Override
	public LlmPrompt planPrompt(TripResponse trip) {
		LlmTripRequest llmTripRequest = llmTripRequestMapper.mapTo(trip);

		return new LlmPrompt(PLAN_SYSTEM_PROMPT,
				"Create a plan for this trip:\n" + jsonMapper.writeValueAsString(llmTripRequest));
	}

	@Override
	public LlmPrompt reviewPrompt(TripResponse trip, PlanVariant variant) {
		LlmTripRequest llmTripRequest = llmTripRequestMapper.mapTo(trip);

		List<LlmPlanItemRequest> items =
				variant.getItems().stream().map(llmPlanItemRequestMapper::mapTo).toList();

		LlmReviewRequest input = new LlmReviewRequest(llmTripRequest, items);

		return new LlmPrompt(REVIEW_SYSTEM_PROMPT, "Review this plan:\n" + jsonMapper.writeValueAsString(input));
	}
}
