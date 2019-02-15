package ivan.kravets.exceptions;

import ivan.kravets.domain.exception.ExceptionResponse;
import ivan.kravets.domain.exception.ValidationExceptionResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class, ServerException.class})
    public ResponseEntity<?> handleAllException(Exception e, WebRequest req) {
        ExceptionResponse exResponse = new ExceptionResponse(e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(exResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExistsException(Exception e, WebRequest req) {
        ExceptionResponse exResponse = new ExceptionResponse(e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(exResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(Exception e, WebRequest req) {
        ExceptionResponse exResponse = new ExceptionResponse(e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(exResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<?> handleNoAccessException(Exception e, WebRequest req) {
        ExceptionResponse exResponse = new ExceptionResponse(e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(exResponse, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
//        List<String> errors = bindingResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        List<String> errors = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        for (int i=0; i<fieldErrors.size(); i++) {
            String resolvable = fieldErrors.get(i).getDefaultMessage();
            errors.add(resolvable);
        }

        return new ResponseEntity<>(new ValidationExceptionResponse("Validation failed", errors), HttpStatus.BAD_REQUEST);
    }
}
