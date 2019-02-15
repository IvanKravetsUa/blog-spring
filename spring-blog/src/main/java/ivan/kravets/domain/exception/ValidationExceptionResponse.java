package ivan.kravets.domain.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ValidationExceptionResponse {

    private String event;
    private List<String> errors;
    private LocalDateTime timestamp;

    public ValidationExceptionResponse(String event, List<String> errors) {
        this.event = event;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }
}
