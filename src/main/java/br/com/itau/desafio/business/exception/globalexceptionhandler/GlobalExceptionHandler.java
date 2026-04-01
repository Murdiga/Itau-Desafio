package br.com.itau.desafio.business.exception.globalexceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.itau.desafio.business.exception.businessexception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerException(Exception e){

        log.error("ERROR >>> ", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handlerBusinessException(BusinessException e){
        
        log.error("ERROR >>> ", e);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(e.getMessage());

    }

}
