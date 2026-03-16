package br.com.itau.desafio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itau.desafio.business.exception.businessexception.BusinessException;
import br.com.itau.desafio.business.usecase.DeleteAllTransitionsUseCase;
import br.com.itau.desafio.business.usecase.TransitionValueUseCase;
import br.com.itau.desafio.infrastructure.dto.TransitionValueRequest;

@RestController
@RequestMapping("/bank")
public class bankController {

    @Autowired
    private TransitionValueUseCase transitionValueUseCase;

    @Autowired
    private DeleteAllTransitionsUseCase deleteAllTransitionsUseCase;

    @PostMapping("/transacao")
    public ResponseEntity<Object> transition(@RequestBody TransitionValueRequest transitionValueRequest){

        try {
            
            transitionValueUseCase.transitionValue(transitionValueRequest);

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (BusinessException e) {

            return ResponseEntity.status(e.getStatus()).body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

    }

    @DeleteMapping("/transacao")
    public ResponseEntity<Object> deleteAllTransitions(){

        try {
            
            deleteAllTransitionsUseCase.deleteAllTrasintions();

            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
