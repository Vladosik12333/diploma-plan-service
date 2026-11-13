package by.vb.diplomaplanservice.dto.trip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TripLocationResponse(String googlePlaceId, String city, String country) {
}
