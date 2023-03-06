package org.example.web.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;

import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public RestResponseExceptionModel handleValidationException(MethodArgumentNotValidException e, Locale locale) {
        log.error(e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();

        return new RestResponseExceptionModel(bindingResult.getAllErrors()
                .stream()
                .map(error -> messageSource.getMessage(error, locale))
                .flatMap(compile(",")::splitAsStream)
                .collect(toList()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(BAD_REQUEST)
    public RestResponseExceptionModel handleOtherException(Exception e) {
        log.error(e.getMessage(), e);
        return new RestResponseExceptionModel(List.of(e.getMessage()));
    }
}
