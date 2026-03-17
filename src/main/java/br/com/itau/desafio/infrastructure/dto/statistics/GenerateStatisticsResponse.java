package br.com.itau.desafio.infrastructure.dto.statistics;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateStatisticsResponse {

    int count;
    BigDecimal sum;
    BigDecimal avg;
    BigDecimal min;
    BigDecimal max;

}
