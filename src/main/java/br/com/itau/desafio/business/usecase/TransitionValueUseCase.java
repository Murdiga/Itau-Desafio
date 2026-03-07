package br.com.itau.desafio.business.usecase;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.itau.desafio.infrastructure.dto.TransitionValueRequest;
import br.com.itau.desafio.infrastructure.respository.TransitionValueRepository;

public class TransitionValueUseCase {

    @Autowired
    private TransitionValueRepository transitionValueRepository;

    public void transitionValue(TransitionValueRequest transitionValueRequest){

        this.transitionValueRepository.save(transitionValueRequest);

    }

}
