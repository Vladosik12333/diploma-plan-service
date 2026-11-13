package by.vb.diplomaplanservice.dto.trip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CategoryResponse(String code, String name, String description) {
}
