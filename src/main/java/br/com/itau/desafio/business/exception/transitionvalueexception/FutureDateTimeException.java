package br.com.itau.desafio.business.exception.transitionvalueexception;

import org.springframework.http.HttpStatus;

import br.com.itau.desafio.business.exception.businessexception.BusinessException;

public class FutureDateTimeException extends BusinessException{

    public FutureDateTimeException() {
        super("The provided date time is after the current date time.", HttpStatus.UNPROCESSABLE_CONTENT);
    }

}
