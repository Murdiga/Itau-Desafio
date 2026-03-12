package br.com.itau.desafio.business.exception.transitionvalueexception;

import org.springframework.http.HttpStatus;

import br.com.itau.desafio.business.exception.businessexception.BusinessException;

public class MissingRequiredFieldException extends BusinessException {

    public MissingRequiredFieldException() {
        super("All fields must be provided to perform the transaction.", HttpStatus.UNPROCESSABLE_CONTENT);
    }

}
