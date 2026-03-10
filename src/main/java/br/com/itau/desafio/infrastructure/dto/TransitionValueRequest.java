package br.com.itau.desafio.infrastructure.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TransitionValueRequest {

    private BigDecimal valor;
    private LocalDateTime dataHora;

}
