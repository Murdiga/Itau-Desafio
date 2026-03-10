package br.com.itau.desafio.business.exception.transitionvalueexception;

public class MissingRequiredFieldException extends RuntimeException {

    public MissingRequiredFieldException() {
        super("All fields must be provided to perform the transaction.");
    }

}
