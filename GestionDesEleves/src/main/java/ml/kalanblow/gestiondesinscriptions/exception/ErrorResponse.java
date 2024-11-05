package ml.kalanblow.gestiondesinscriptions.exception;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ErrorResponse(int statusCode, String message, @JsonFormat(pattern = "dd/MM-yyyy HH:mm:ss") LocalDateTime timestamp) {
}
