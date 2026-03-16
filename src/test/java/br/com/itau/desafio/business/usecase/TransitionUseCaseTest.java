package br.com.itau.desafio.business.usecase;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.itau.desafio.business.exception.transitionvalueexception.FutureDateTimeException;
import br.com.itau.desafio.business.exception.transitionvalueexception.MissingRequiredFieldException;
import br.com.itau.desafio.business.exception.transitionvalueexception.NegativeValueException;
import br.com.itau.desafio.infrastructure.dto.TransitionValueRequest;
import br.com.itau.desafio.infrastructure.respository.TransitionValueRepository;

@ExtendWith(MockitoExtension.class)
public class TransitionUseCaseTest {

    @Mock
    private TransitionValueRepository transitionValueRepository;

    @InjectMocks
    private TransitionValueUseCase transitionValueUseCase;

    @InjectMocks
    private DeleteAllTransitionsUseCase deleteAllTransitionsUseCase;

    @Test
    public void shouldTransitionValue(){

        TransitionValueRequest transitionValueRequest = new TransitionValueRequest();
        ArgumentCaptor<TransitionValueRequest> captor = ArgumentCaptor.forClass(TransitionValueRequest.class);

        transitionValueRequest.setValor(BigDecimal.valueOf(100.50));
        transitionValueRequest.setDataHora(LocalDateTime.now());

        transitionValueUseCase.transitionValue(transitionValueRequest);

        verify(transitionValueRepository).save(captor.capture());

        var transition = captor.getValue();
        
        assertAll(
            () -> assertEquals(BigDecimal.valueOf(100.50), transition.getValor()),
            () -> assertEquals(transitionValueRequest.getDataHora(), transition.getDataHora())
        );
    }

    @Test
    public void shouldNotTransitionValueWithFutureDateTime(){

        TransitionValueRequest transitionValueRequest = new TransitionValueRequest();

        transitionValueRequest.setValor(BigDecimal.valueOf(100.50));
        transitionValueRequest.setDataHora(LocalDateTime.now().plusDays(1));

        var exception = assertThrows(FutureDateTimeException.class, () -> {
            transitionValueUseCase.transitionValue(transitionValueRequest);
        });

        assertEquals("The provided date time is after the current date time.", exception.getMessage());

    }

    @Test
    public void shouldNotTransitionValueWithNegativeValue(){

        TransitionValueRequest transitionValueRequest = new TransitionValueRequest();

        transitionValueRequest.setValor(BigDecimal.valueOf(-20));
        transitionValueRequest.setDataHora(LocalDateTime.now());

        var exception = assertThrows(NegativeValueException.class, () -> {
            transitionValueUseCase.transitionValue(transitionValueRequest);
        });

        assertEquals("Value cannot be negative.", exception.getMessage());

    }

    @Test
    public void shouldNotTransitionValueWithMissingRequiredField(){

        TransitionValueRequest transitionValueRequest = new TransitionValueRequest();

        transitionValueRequest.setValor(null);
        transitionValueRequest.setDataHora(null);

        var exception = assertThrows(MissingRequiredFieldException.class, () -> {
            transitionValueUseCase.transitionValue(transitionValueRequest);
        });

        assertEquals("All fields must be provided to perform the transaction.", exception.getMessage());

    }

    @Test
    public void shouldClearAllTransitions(){

        deleteAllTransitionsUseCase.deleteAllTrasintions();

        verify(transitionValueRepository).delete();

    }

}
