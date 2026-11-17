package by.vb.diplomaplanservice.client.llm.impl;

import by.vb.diplomaplanservice.client.llm.PromptFactory;
import by.vb.diplomaplanservice.dto.trip.TripResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PromptFactoryImpl implements PromptFactory {
	private static final SystemPromptTemplate PLAN_SYSTEM_TEMPLATE =
			new SystemPromptTemplate(new ClassPathResource("prompts/plan-system.st"));
	private static final PromptTemplate PLAN_USER_TEMPLATE =
			new PromptTemplate(new ClassPathResource("prompts/plan-user.st"));

	@Override
	public Prompt planPrompt(final TripResponse trip) {
		final Map<String, Object> variables = new HashMap<>();

		variables.put("title", trip.title());
		variables.put("city", trip.location().city());
		variables.put("country", trip.location().country());
		variables.put("startDate", trip.startDate());
		variables.put("endDate", trip.endDate());
		variables.put("budgetAmount", trip.budgetAmount().toPlainString());
		variables.put("currency", trip.currency());
		variables.put("categories", trip.categories().stream()
				.map(category -> Map.of(
						"code", category.code(),
						"name", category.name(),
						"description", category.description()))
				.toList());

		return new Prompt(List.of(PLAN_SYSTEM_TEMPLATE.createMessage(), PLAN_USER_TEMPLATE.createMessage(variables)));
	}
}
