package br.com.itau.desafio.controller;

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
import br.com.itau.desafio.infrastructure.dto.exception.ExceptionResponse;
import br.com.itau.desafio.infrastructure.dto.statistics.GenerateStatisticsRequest;
import br.com.itau.desafio.infrastructure.dto.transitionValue.TransitionValueRequest;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Create a new transition", description = "Endpoint to create a new transition with the provided value and type.")
    @ApiResponse(responseCode = "201", description = "Transition created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request payload", content = {
        @Content(schema = @Schema(implementation = ExceptionResponse.class))
    })
    @ApiResponse(responseCode = "422", description = "Business validation failed", content = {
        @Content(schema = @Schema(implementation = ExceptionResponse.class))
    })
    public ResponseEntity<Object> transition(@RequestBody TransitionValueRequest transitionValueRequest){
        
        log.info("Accepted request to create a new transition");

        transitionValueUseCase.transitionValue(transitionValueRequest);

        log.debug("New transition payload: {}", transitionValueRequest);

        log.info("Successfully created a new transition");

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/transacao")
    @Operation(summary = "Delete all transitionsa", description = "Endpoint to delete all transitions from the system.")
    @ApiResponse(responseCode = "200", description = "All transitions deleted successfully")
    @ApiResponse(responseCode = "400", description = "Bad request", content = {
        @Content(schema = @Schema(implementation = ExceptionResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal server error", content = {
        @Content(schema = @Schema(implementation = ExceptionResponse.class))
    })
    public ResponseEntity<Object> deleteAllTransitions(){

        log.info("Accepted request to delete all transitions");

        deleteAllTransitionsUseCase.deleteAllTrasintions();

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/estatistica")
    @Operation(summary = "Generate statistics", description = "Endpoint to generate statistics based on the existing transitions.")
    @ApiResponse(responseCode = "200", description = "Statistics generated successfully")
    @ApiResponse(responseCode = "400", description = "Bad request", content = {
        @Content(schema = @Schema(implementation = ExceptionResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal server error", content = {
        @Content(schema = @Schema(implementation = ExceptionResponse.class))
    })
    public ResponseEntity<Object> getStatistics(@RequestBody(required = false) GenerateStatisticsRequest generateStatisticsRequest){

        log.info("Accepted request to generate statistics");

        Timer.Sample sample = Timer.start(meterRegistry);

        try{

            var stats = generateStatisticsUseCase.generateStatistics(generateStatisticsRequest);

            log.debug("Statistics generated: {}", stats);

            return ResponseEntity.status(HttpStatus.OK).body(stats);

        }finally{

            long duration = sample.stop(meterRegistry.timer("bank.statistics.execution"));

            long durationMillis = TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS);

            log.info("Time to generate statistics: {} ms", durationMillis);

        }

    }

    @GetMapping("metrics/statistics")
    @Operation(summary = "Get custom metrics", description = "Endpoint to retrieve custom metrics related to statistics generation.")
    @ApiResponse(responseCode = "200", description = "Custom metrics retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Bad request", content = {
        @Content(schema = @Schema(implementation = ExceptionResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal server error", content = {
        @Content(schema = @Schema(implementation = ExceptionResponse.class))
    })
    public ResponseEntity<Map<String, Object>> getCustomMetrics() {

        Timer timer = meterRegistry.timer("bank.statistics.execution");

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
