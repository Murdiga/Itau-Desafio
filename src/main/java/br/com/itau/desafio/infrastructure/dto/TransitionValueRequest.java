package br.com.itau.desafio.infrastructure.dto;

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

    private Double valor;
    private LocalDateTime dataHora;

}
