package br.com.itau.desafio.business.exception.transitionvalueexception;

public class FutureDateTimeException extends RuntimeException{

    public FutureDateTimeException() {
        super("The provided date time is after the current date time.");
    }

}
