package br.com.itau.desafio.business.exception.transitionvalueexception;

import org.springframework.http.HttpStatus;

import br.com.itau.desafio.business.exception.businessexception.BusinessException;

public class NegativeValueException extends BusinessException {

    public NegativeValueException() {

        super("Value cannot be negative.", HttpStatus.UNPROCESSABLE_CONTENT);

    }

}
