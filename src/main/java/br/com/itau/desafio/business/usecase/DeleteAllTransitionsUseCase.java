package br.com.itau.desafio.business.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.itau.desafio.infrastructure.respository.TransitionValueRepository;

@Service
public class DeleteAllTransitionsUseCase {

    @Autowired
    private TransitionValueRepository transitionValueRepository;

    public void deleteAllTrasintions(){

        this.transitionValueRepository.delete();

    }
    
}
