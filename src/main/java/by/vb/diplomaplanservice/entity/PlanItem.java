package by.vb.diplomaplanservice.entity;

import by.vb.diplomaplanservice.entity.enums.PlanItemIssue;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "plan_items")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "planVariant")
@ToString(exclude = "planVariant")
public class PlanItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "plan_variant_id", nullable = false)
	private PlanVariant planVariant;

	@Column(name = "day_date", nullable = false)
	private LocalDate dayDate;

	@Column(nullable = false)
	private Integer position;

	@Column(nullable = false)
	private String name;

	@Column(name = "google_place_id")
	private String googlePlaceId;

	@Column(length = 64)
	private String category;

	@Column(name = "estimated_duration_min")
	private Integer estimatedDurationMin;

	@Column(precision = 9, scale = 6)
	private BigDecimal latitude;

	@Column(precision = 9, scale = 6)
	private BigDecimal longitude;

	@Column(length = 500)
	private String address;

	@Column(name = "start_time")
	private LocalTime startTime;

	@Column(name = "end_time")
	private LocalTime endTime;

	@Column(name = "estimated_cost")
	private Integer estimatedCost;

	@Column(name = "open_time")
	private LocalTime openTime;

	@Column(name = "close_time")
	private LocalTime closeTime;

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private PlanItemIssue issue;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;
}
