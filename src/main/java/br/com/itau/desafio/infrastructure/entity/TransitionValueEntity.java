package br.com.itau.desafio.infrastructure.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransitionValueEntity {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Double valor;

    LocalDateTime dataHora;

}
