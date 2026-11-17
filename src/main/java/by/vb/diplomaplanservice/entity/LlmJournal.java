package by.vb.diplomaplanservice.entity;

import by.vb.diplomaplanservice.entity.enums.LlmCallStatus;
import by.vb.diplomaplanservice.entity.enums.LlmCallType;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "llm_journal")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"systemPrompt", "userPrompt", "response"})
public class LlmJournal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "generated_plan_id", nullable = false)
	private Long generatedPlanId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private LlmCallType type;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private LlmProvider provider;

	@Column(length = 100)
	private String model;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private LlmCallStatus status;

	@Column(name = "input_tokens")
	private Integer inputTokens;

	@Column(name = "output_tokens")
	private Integer outputTokens;

	@Column(name = "duration_ms", nullable = false)
	private Long durationMs;

	@Column(name = "error_message", length = 500)
	private String errorMessage;

	@Column(name = "system_prompt", nullable = false, columnDefinition = "TEXT")
	private String systemPrompt;

	@Column(name = "user_prompt", nullable = false, columnDefinition = "TEXT")
	private String userPrompt;

	@Column(columnDefinition = "TEXT")
	private String response;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
}
