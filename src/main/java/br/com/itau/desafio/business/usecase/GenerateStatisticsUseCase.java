package br.com.itau.desafio.business.usecase;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.itau.desafio.infrastructure.dto.statistics.GenerateStatisticsRequest;
import br.com.itau.desafio.infrastructure.dto.statistics.GenerateStatisticsResponse;
import br.com.itau.desafio.infrastructure.entity.TransitionValueEntity;
import br.com.itau.desafio.infrastructure.respository.TransitionValueRepository;

@Service
public class GenerateStatisticsUseCase {

    @Autowired
    private TransitionValueRepository transitionValueRepository;

    public GenerateStatisticsResponse generateStatistics(GenerateStatisticsRequest generateStatisticsRequest){

        if (generateStatisticsRequest == null) {
            
            generateStatisticsRequest = new GenerateStatisticsRequest();

            generateStatisticsRequest.setMinutes(1);

        }

        int count = 0;
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal min = BigDecimal.valueOf(Double.MAX_VALUE);
        BigDecimal max = BigDecimal.valueOf(Double.MIN_VALUE);

        GenerateStatisticsResponse generateStatisticsResponse = new GenerateStatisticsResponse();

        var result = transitionValueRepository.findLastMinuteTransitions(generateStatisticsRequest.getMinutes());

        if (result.isEmpty()) {
            
            return generateStatisticsResponse = GenerateStatisticsResponse.builder()
                                                                          .count(0)
                                                                          .sum(BigDecimal.ZERO)
                                                                          .avg(BigDecimal.ZERO)
                                                                          .min(BigDecimal.ZERO)
                                                                          .max(BigDecimal.ZERO)
                                                                          .build();

        }

        for(TransitionValueEntity item : result){

            count++;

            sum = sum.add(item.getValor());

            min = min.min(item.getValor());

            max = max.max(item.getValor());

        }

        var avg = sum.divide(BigDecimal.valueOf(count));

        generateStatisticsResponse = GenerateStatisticsResponse.builder()
                                                               .count(count)
                                                               .avg(avg)
                                                               .min(min)
                                                               .max(max)
                                                               .sum(sum)
                                                               .build();

        return generateStatisticsResponse;

    }

}
