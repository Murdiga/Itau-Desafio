package br.com.itau.desafio.infrastructure.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateStatisticsRequest {

    private int minutes;

}
