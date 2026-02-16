package com.codewithmosh.store.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.codewithmosh.store.dtos.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErrorDto> handleHttpMessageNotReadable(
                HttpMessageNotReadableException ex) {
            return ResponseEntity.badRequest().body(new ErrorDto("Invalid request body"));
        }

    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map< String , String >> handlerVlidationErorrs(
            MethodArgumentNotValidException ex
        ){
            var erorre = new HashMap<String , String >();
            ex.getBindingResult().getFieldErrors().forEach(e -> erorre.put(e.getField(),e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(erorre);

        }
}
