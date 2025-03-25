package com.hiberus.prices.infrastruture.adapter.in.rest;

import com.hiberus.prices.models.Error;
import com.hiberus.prices.domain.exception.PriceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGeneralException(Exception ex, HttpServletRequest request) {
        Error errorResponse = new Error();
        errorResponse.setTimestamp(OffsetDateTime.now());
        errorResponse.setPath(request.getRequestURI());
        //para controlar las busquedas sin resultados
        if (ex instanceof PriceNotFoundException priceNotFoundException) {
            errorResponse.setTimestamp(OffsetDateTime.now());
            errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
            errorResponse.setError("Not Found");
            errorResponse.setMessage(priceNotFoundException.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            //Para errores de formato
        } else if (ex instanceof IllegalArgumentException illegalArgumentException) {
            errorResponse.setTimestamp(OffsetDateTime.now());
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            errorResponse.setError("Bad Request");
            errorResponse.setMessage(illegalArgumentException.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } else {
            // Manejo genérico para otras excepciones
            errorResponse.setTimestamp(OffsetDateTime.now());
            errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.setError("Internal Server Error");
            errorResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

