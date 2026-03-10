package br.com.itau.desafio.business.usecase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.itau.desafio.business.exception.FutureDateTimeException;
import br.com.itau.desafio.business.exception.NegativeValueException;
import br.com.itau.desafio.infrastructure.dto.TransitionValueRequest;
import br.com.itau.desafio.infrastructure.respository.TransitionValueRepository;

public class TransitionValueUseCase {

    @Autowired
    private TransitionValueRepository transitionValueRepository;

    public void transitionValue(TransitionValueRequest transitionValueRequest){

        if (transitionValueRequest.getDataHora().isAfter(LocalDateTime.now())) {
            
            throw new FutureDateTimeException();

        }

        if (transitionValueRequest.getValor().compareTo(BigDecimal.ZERO) < 0) {

            throw new NegativeValueException();
            
        }

        this.transitionValueRepository.save(transitionValueRequest);

    }

}
