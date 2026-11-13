package by.vb.diplomaplanservice.dto;

import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GeneratePlanRequest(@NotNull @Positive Long tripId, @NotNull LlmProvider provider) {
}
