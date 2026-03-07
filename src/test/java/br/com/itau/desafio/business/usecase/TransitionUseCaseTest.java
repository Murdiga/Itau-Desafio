package br.com.itau.desafio.business.usecase;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.itau.desafio.infrastructure.dto.TransitionValueRequest;
import br.com.itau.desafio.infrastructure.respository.TransitionValueRepository;

@ExtendWith(MockitoExtension.class)
public class TransitionUseCaseTest {

    @Mock
    private TransitionValueRepository transitionValueRepository;

    @InjectMocks
    private TransitionValueUseCase transitionValueUseCase;

    @Test
    public void shouldTransitionValue(){

        TransitionValueRequest transitionValueRequest = new TransitionValueRequest();
        ArgumentCaptor<TransitionValueRequest> captor = ArgumentCaptor.forClass(TransitionValueRequest.class);

        transitionValueRequest.setValor(100.50);
        transitionValueRequest.setDataHora(LocalDateTime.now());

        transitionValueUseCase.transitionValue(transitionValueRequest);

        verify(transitionValueRepository).save(captor.capture());

        var transition = captor.getValue();
        
        assertAll(
            () -> assertEquals(100.50, transition.getValor()),
            () -> assertEquals(transitionValueRequest.getDataHora(), transition.getDataHora())
        );
    }

}
