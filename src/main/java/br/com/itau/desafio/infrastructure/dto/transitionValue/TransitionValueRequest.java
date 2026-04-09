package br.com.itau.desafio.infrastructure.dto.transitionValue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransitionValueRequest {

    private BigDecimal valor;
    private LocalDateTime dataHora;

}
