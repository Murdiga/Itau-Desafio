package br.com.itau.desafio.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itau.desafio.business.usecase.DeleteAllTransitionsUseCase;
import br.com.itau.desafio.business.usecase.GenerateStatisticsUseCase;
import br.com.itau.desafio.business.usecase.TransitionValueUseCase;
import br.com.itau.desafio.infrastructure.dto.transitionValue.TransitionValueRequest;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/bank")
@Slf4j
public class BankController {

    @Autowired
    private TransitionValueUseCase transitionValueUseCase;

    @Autowired
    private DeleteAllTransitionsUseCase deleteAllTransitionsUseCase;

    @Autowired
    private GenerateStatisticsUseCase generateStatisticsUseCase;

    @Autowired
    private MeterRegistry meterRegistry;

    @PostMapping("/transacao")
    public ResponseEntity<Object> transition(@RequestBody TransitionValueRequest transitionValueRequest){
        
        log.info("Accepted request to create a new transition");

        transitionValueUseCase.transitionValue(transitionValueRequest);

        log.debug("New transition payload: {}", transitionValueRequest);

        log.info("Successfully created a new transition");

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/transacao")
    public ResponseEntity<Object> deleteAllTransitions(){

        log.info("Accepted request to delete all transitions");

        deleteAllTransitionsUseCase.deleteAllTrasintions();

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/estatistica")
    public ResponseEntity<Object> getStatistics(){

        log.info("Accepted request to generate statistics");

        Timer.Sample sample = Timer.start(meterRegistry);

        try{

            var stats = generateStatisticsUseCase.generateStatistics();

            log.debug("Statistics generated: {}", stats);

            return ResponseEntity.status(HttpStatus.OK).body(stats);

        }finally{

            long duration = sample.stop(meterRegistry.timer("bank.statistics.execution"));

            long durationMillis = TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS);

            log.info("Time to generate statistics: {} ms", durationMillis);

        }

    }

    @GetMapping("metrics/statistics")
    public ResponseEntity<Map<String, Object>> getCustomMetrics() {

        Timer timer = meterRegistry.find("bank.statistics.execution").timer();

        double totalTime = timer.totalTime(TimeUnit.SECONDS);

        long count = timer.count();

        double avg = totalTime / count;

        Map<String, Object> metrics = Map.of(
            "totalTime", totalTime,
            "count", count,
            "max", timer.max(TimeUnit.SECONDS),
            "averageTime", avg
        );

        return ResponseEntity.status(HttpStatus.OK).body(metrics);
    }
    

}
