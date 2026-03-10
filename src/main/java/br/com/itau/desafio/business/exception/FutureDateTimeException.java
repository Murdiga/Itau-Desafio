package br.com.itau.desafio.business.exception;

public class FutureDateTimeException extends RuntimeException{

    public FutureDateTimeException() {
        super("The provided date time is after the current date time.");
    }

}
