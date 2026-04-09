package br.com.itau.desafio.infrastructure.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private String message;
    private int status;
    private String timestamp;

}
