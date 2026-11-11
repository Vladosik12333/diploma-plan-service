package by.vb.diplomaplanservice.dto;


public record BasicResponse<T>(T data, String message, boolean success) {
}