package br.com.itau.desafio.business.exception.transitionvalueexception;

public class NegativeValueException extends RuntimeException {

    public NegativeValueException() {

        super("Value cannot be negative.");

    }

}
