package by.vb.diplomaplanservice.entity;

import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "plan_reviews")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "planVariant")
@ToString(exclude = "planVariant")
public class PlanReview {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "plan_variant_id", nullable = false, unique = true)
	private PlanVariant planVariant;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private LlmProvider provider;

	@Column(nullable = false, length = 100)
	private String model;

	@Column(name = "preference_fit")
	private Integer preferenceFit;

	private Integer variety;

	private Integer pacing;

	private Integer coherence;

	@Column(name = "budget_fit")
	private Integer budgetFit;

	@Column(name = "overall_score")
	private Integer overallScore;

	@Column(length = 500)
	private String summary;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;
}
