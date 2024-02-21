package com.wcs.mtgbox.filters.infrastructure.exception;

import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FiltersNotFoundErrorExceptionAdvice {
    @ExceptionHandler(SetsNotFoundErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String errorGetSetsNotFound(SetsNotFoundErrorException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(TypesNotFoundErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String errorGetTypesNotFound(TypesNotFoundErrorException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(QualitiesNotFoundErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String errorGetQualitiesNotFound(QualitiesNotFoundErrorException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(RaritiesNotFoundErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String errorGetRaritiesNotFound(RaritiesNotFoundErrorException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(LanguagesNotFoundErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String errorGetRaritiesNotFound(LanguagesNotFoundErrorException ex) {
        return ex.getMessage();
    }
}
