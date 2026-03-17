package br.com.itau.desafio.business.usecase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.itau.desafio.business.exception.transitionvalueexception.FutureDateTimeException;
import br.com.itau.desafio.business.exception.transitionvalueexception.MissingRequiredFieldException;
import br.com.itau.desafio.business.exception.transitionvalueexception.NegativeValueException;
import br.com.itau.desafio.infrastructure.dto.transitionValue.TransitionValueRequest;
import br.com.itau.desafio.infrastructure.respository.TransitionValueRepository;

@Service
public class TransitionValueUseCase {

    @Autowired
    private TransitionValueRepository transitionValueRepository;

    public void transitionValue(TransitionValueRequest transitionValueRequest){

        if (transitionValueRequest.getValor() == null || transitionValueRequest.getDataHora() == null) {

            throw new MissingRequiredFieldException();
            
        }

        if (transitionValueRequest.getValor().compareTo(BigDecimal.ZERO) < 0) {

            throw new NegativeValueException();
            
        }

        if (transitionValueRequest.getDataHora().isAfter(LocalDateTime.now())) {
            
            throw new FutureDateTimeException();

        }

        this.transitionValueRepository.save(transitionValueRequest);

    }

}
